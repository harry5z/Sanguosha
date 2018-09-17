package core.server.game.controllers;

import cards.basics.Attack;
import core.event.game.basic.AttackEndEvent;
import core.event.game.basic.AttackOnDodgedWeaponAbilitiesCheckEvent;
import core.event.game.basic.AttackPreDamageWeaponAbilitiesCheckEvent;
import core.event.game.basic.AttackTargetEquipmentCheckEvent;
import core.event.game.damage.AttackDamageModifierEvent;
import core.player.PlayerCompleteServer;
import core.server.game.Damage;
import core.server.game.Game;
import core.server.game.controllers.interfaces.DodgeUsableGameController;
import exceptions.server.game.GameFlowInterruptedException;
import utils.EnumWithNextStage;

public class AttackResolutionGameController extends AbstractGameController implements DodgeUsableGameController {
	
	public static enum AttackResolutionStage implements EnumWithNextStage<AttackResolutionStage> {
		TARGET_EQUIPMENT_ABILITIES,
		DODGE,
		ATTACK_DODGED_WEAPONS,
		PRE_DAMAGE_WEAPON_ABILITIES,
		DAMAGE_MODIFIERS,
		DAMAGE,
		END;
	}
	
	private AttackResolutionStage stage;
	private final PlayerCompleteServer source;
	private final PlayerCompleteServer target;
	private Damage damage;
	private final Attack attack;

	public AttackResolutionGameController(PlayerCompleteServer source, PlayerCompleteServer target, Attack card, int damage, Game game) {
		super(game);
		this.source = source;
		this.target = target;
		this.attack = card;
		this.damage = new Damage(damage, this.attack.getElement(), this.source, this.target);
		this.stage = AttackResolutionStage.TARGET_EQUIPMENT_ABILITIES;
	}

	@Override
	public void proceed() {
		switch(this.stage) {
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
					this.game.emit(new AttackOnDodgedWeaponAbilitiesCheckEvent(this.source, this.target, this));
					this.stage = AttackResolutionStage.END;
					this.game.getGameController().proceed();
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
				try {
					this.game.emit(new AttackEndEvent(this.source, this.target));
				} catch (GameFlowInterruptedException e) {
					// should not receive GameFlowInterruptedException
				}
				this.onUnloaded();
				this.game.getGameController().proceed();
				break;
		}
	}

	@Override
	public void onDodged() {
		this.stage = AttackResolutionStage.ATTACK_DODGED_WEAPONS;
	}

	@Override
	public void onNotDodged() {
		this.stage = AttackResolutionStage.PRE_DAMAGE_WEAPON_ABILITIES;
	}
	
	public void setStage(AttackResolutionStage stage) {
		this.stage = stage;
	}
	
}
