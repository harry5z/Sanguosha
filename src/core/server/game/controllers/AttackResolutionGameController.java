package core.server.game.controllers;

import java.util.HashSet;
import java.util.Set;

import cards.basics.Attack;
import core.event.game.basic.AttackLockedSourceWeaponAbilitiesCheckEvent;
import core.event.game.basic.AttackLockedTargetSkillsCheckEvent;
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
		TARGET_LOCKED_TARGET_HERO_SKILLS,
		TARGET_LOCKED_SOURCE_WEAPON_ABILITIES,
		TARGET_LOCKED_TARGET_EQUIPMENT_ABILITIES,
		DODGE,
		ATTACK_DODGED_SOURCE_WEAPON_ABILITIES,
		PRE_DAMAGE_SOURCE_WEAPON_ABILITIES,
		PRE_DAMAGE_SOURCE_WEAPON_DAMAGE_MODIFIERS,
		APPLY_DAMAGE,
		END;
	}
	
	private AttackResolutionStage stage;
	private final PlayerCompleteServer source;
	private final PlayerCompleteServer target;
	private final Damage damage;
	private final Attack attack;
	private final Set<AttackResolutionStage> skippedStages;
	public final DodgeGameController dodgeController;
	public final DamageGameController damageController;

	public AttackResolutionGameController(PlayerCompleteServer source, PlayerCompleteServer target, Attack card, int damage, Game game) {
		super(game);
		this.source = source;
		this.target = target;
		this.attack = card;
		this.damage = new Damage(damage, this.attack.getElement(), this.source, this.target);
		this.stage = AttackResolutionStage.TARGET_LOCKED_TARGET_HERO_SKILLS;
		this.skippedStages = new HashSet<>();
		this.dodgeController = new DodgeGameController(
			this.game,
			this.target,
			this.source + " used " + this.attack + " on you, use Dodge to counter?"
		);
		this.damageController = new DamageGameController(this.damage, game);
	}

	@Override
	public void proceed() {
		if (this.skippedStages.contains(this.stage)) {
			this.stage = this.stage.nextStage();
			this.proceed();
			return;
		}
		switch(this.stage) {
			case TARGET_LOCKED_TARGET_HERO_SKILLS:
				try {
					this.game.emit(new AttackLockedTargetSkillsCheckEvent(this.source, this.target, this));
					this.stage = this.stage.nextStage();
					this.proceed();
				} catch (GameFlowInterruptedException e) {
					e.resume();
				}
				break;
			case TARGET_LOCKED_SOURCE_WEAPON_ABILITIES:
				try {
					this.game.emit(new AttackLockedSourceWeaponAbilitiesCheckEvent(this.source, this.target, this));
					this.stage = this.stage.nextStage();
					this.proceed();
				} catch (GameFlowInterruptedException e) {
					e.resume();
				}
				break;
			case TARGET_LOCKED_TARGET_EQUIPMENT_ABILITIES:
				try {
					this.game.emit(new AttackTargetEquipmentCheckEvent(this.target.getPlayerInfo(), this.attack, this));
					this.stage = this.stage.nextStage();
					this.proceed();
				} catch (GameFlowInterruptedException e) {
					e.resume();
				}
				break;
			case DODGE:
				this.game.pushGameController(this.dodgeController);
				this.game.getGameController().proceed();
				break;
			case ATTACK_DODGED_SOURCE_WEAPON_ABILITIES:
				try {
					this.game.emit(new AttackOnDodgedWeaponAbilitiesCheckEvent(this.source, this.target, this));
					this.stage = AttackResolutionStage.END;
					this.game.getGameController().proceed();
				} catch (GameFlowInterruptedException e) {
					e.resume();
				}
				break;
			case PRE_DAMAGE_SOURCE_WEAPON_ABILITIES:
				try {
					this.game.emit(new AttackPreDamageWeaponAbilitiesCheckEvent(this.source, this.target, this));
					this.stage = this.stage.nextStage();
					this.proceed();
				} catch (GameFlowInterruptedException e) {
					e.resume();
				}
				break;
			case PRE_DAMAGE_SOURCE_WEAPON_DAMAGE_MODIFIERS:
				try {
					this.game.emit(new AttackDamageModifierEvent(this.damage));
					this.stage = this.stage.nextStage();
					this.proceed();
				} catch (GameFlowInterruptedException e) {
					e.resume();
				}
				break;
			case APPLY_DAMAGE:
				game.pushGameController(this.damageController);
				stage = stage.nextStage();
				game.getGameController().proceed();
				break;
			case END:
				this.onUnloaded();
				this.game.getGameController().proceed();
				break;
		default:
			break;
		}
	}

	@Override
	public void onDodged() {
		this.stage = AttackResolutionStage.ATTACK_DODGED_SOURCE_WEAPON_ABILITIES;
	}

	@Override
	public void onNotDodged() {
		this.stage = AttackResolutionStage.PRE_DAMAGE_SOURCE_WEAPON_ABILITIES;
	}
	
	public void setStage(AttackResolutionStage stage) {
		this.stage = stage;
	}
	
	public void skipStage(AttackResolutionStage stage) {
		this.skippedStages.add(stage);
	}
	
	public Attack getAttackCard() {
		return this.attack;
	}
	
}
