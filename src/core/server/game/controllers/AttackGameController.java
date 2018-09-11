package core.server.game.controllers;

import cards.basics.Attack;
import core.event.game.basic.AttackOnDodgedWeaponAbilitiesCheckEvent;
import core.event.game.basic.AttackOnLockWeaponAbilitiesCheckEvent;
import core.event.game.basic.AttackPreDamageWeaponAbilitiesCheckEvent;
import core.event.game.basic.AttackTargetEquipmentCheckEvent;
import core.event.game.damage.AttackDamageModifierEvent;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.game.Damage;
import core.server.game.Game;
import core.server.game.controllers.interfaces.DodgeUsableGameController;
import exceptions.server.game.GameFlowInterruptedException;
import utils.EnumWithNextStage;

public class AttackGameController extends AbstractGameController implements DodgeUsableGameController {

	public static enum AttackStage implements EnumWithNextStage<AttackStage> {
		TARGET_LOCKED,
		AFTER_TARGET_LOCKED_SKILLS,
		AFTER_TARGET_LOCKED_WEAPONS,
		TARGET_EQUIPMENT_ABILITIES,
		DODGE,
		ATTACK_DODGED_WEAPONS,
		PRE_DAMAGE_WEAPON_ABILITIES,
		DAMAGE_MODIFIERS,
		DAMAGE,
		END;
	}
	
	private AttackStage stage;
	private PlayerCompleteServer source;
	private PlayerCompleteServer target;
	private Damage damage;
	private Attack attack;
	
	public AttackGameController(PlayerInfo source, PlayerInfo target, Attack card, Game game) {
		super(game);
		this.stage = AttackStage.TARGET_LOCKED;
		this.source = game.findPlayer(source);
		this.target = game.findPlayer(target);
		this.damage = new Damage(this.source, this.target);
		if (card != null) {
			this.attack = card;
		} else {
			this.attack = new Attack();
		}
	}
	
	@Override
	public void proceed() {
		switch (stage) {
			case TARGET_LOCKED:
				if (source.isWineEffective()) {
					this.damage.setAmount(this.damage.getAmount() + 1);
					this.source.resetWineEffective();
				}
				this.stage = this.stage.nextStage();
				this.proceed();
				break;
			case AFTER_TARGET_LOCKED_SKILLS:
				stage = stage.nextStage();
				proceed();
				break;
			case AFTER_TARGET_LOCKED_WEAPONS:
				try {
					this.game.emit(new AttackOnLockWeaponAbilitiesCheckEvent(this.source, this.target, this));
					this.stage = this.stage.nextStage();
					this.proceed();
				} catch (GameFlowInterruptedException e) {
					e.resume();
				}
				break;
			case TARGET_EQUIPMENT_ABILITIES:
				try {
					this.game.emit(new AttackTargetEquipmentCheckEvent(this.target.getPlayerInfo(), this.attack, this));
					this.stage = this.stage.nextStage();
					this.proceed();
				} catch (GameFlowInterruptedException e) {
					e.resume();
				}
				break;
			case DODGE:
				this.game.pushGameController(new DodgeGameController(
					this.game,
					this.target,
					this.source + " used " + this.attack + " on you, use Dodge to counter?"
				));
				this.game.getGameController().proceed();
				break;
			case ATTACK_DODGED_WEAPONS:
				try {
					this.game.emit(new AttackOnDodgedWeaponAbilitiesCheckEvent(this.source, this));
					this.stage = AttackStage.END;
					this.proceed();
				} catch (GameFlowInterruptedException e) {
					e.resume();
				}
				break;
			case PRE_DAMAGE_WEAPON_ABILITIES:
				try {
					this.game.emit(new AttackPreDamageWeaponAbilitiesCheckEvent(this.source, this.target, this));
					this.stage = this.stage.nextStage();
					this.proceed();
				} catch (GameFlowInterruptedException e) {
					e.resume();
				}
				break;
			case DAMAGE_MODIFIERS:
				try {
					this.damage.setElement(this.attack.getElement());
					this.game.emit(new AttackDamageModifierEvent(this.damage));
					this.stage = this.stage.nextStage();
					this.proceed();
				} catch (GameFlowInterruptedException e) {
					e.resume();
				}
				break;
			case DAMAGE:
				game.pushGameController(new DamageGameController(damage, game));
				stage = stage.nextStage();
				game.getGameController().proceed();
				break;
			case END:
				this.onUnloaded();
				this.game.getGameController().proceed();
				break;
		}
	}

	@Override
	public void onDodged() {
		this.stage = AttackStage.ATTACK_DODGED_WEAPONS;
	}

	@Override
	public void onNotDodged() {
		this.stage = AttackStage.PRE_DAMAGE_WEAPON_ABILITIES;
	}
	
	public void setStage(AttackStage stage) {
		this.stage = stage;
	}
	
	public Attack getAttackCard() {
		return this.attack;
	}
	
	public void setAttackCard(Attack card) {
		this.attack = card;
	}

}
