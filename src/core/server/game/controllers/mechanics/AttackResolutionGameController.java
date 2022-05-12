package core.server.game.controllers.mechanics;

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
import core.server.game.controllers.AbstractGameController;
import core.server.game.controllers.DodgeUsableGameController;
import core.server.game.controllers.GameControllerStage;
import exceptions.server.game.GameFlowInterruptedException;

public class AttackResolutionGameController
	extends AbstractGameController<AttackResolutionGameController.AttackResolutionStage>
	implements DodgeUsableGameController {
	
	public static enum AttackResolutionStage implements GameControllerStage<AttackResolutionStage> {
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
	
	private final PlayerCompleteServer source;
	private final PlayerCompleteServer target;
	private final Damage damage;
	private final Attack attack;
	private final Set<AttackResolutionStage> skippedStages;
	public final DodgeGameController dodgeController;
	public final DamageGameController damageController;

	public AttackResolutionGameController(PlayerCompleteServer source, PlayerCompleteServer target, Attack card, int damage) {
		this.source = source;
		this.target = target;
		this.attack = card;
		this.damage = new Damage(damage, this.attack.getElement(), this.source, this.target);
		this.skippedStages = new HashSet<>();
		this.dodgeController = new DodgeGameController(
			this,
			this.target,
			this.source + " used " + this.attack + " on you, use Dodge to counter?"
		);
		this.damageController = new DamageGameController(this.damage);
	}

	@Override
	protected void handleStage(Game game, AttackResolutionStage stage) throws GameFlowInterruptedException {
		if (this.skippedStages.contains(stage)) {
			this.nextStage();
			return;
		}
		switch(stage) {
			case TARGET_LOCKED_TARGET_HERO_SKILLS:
				this.nextStage();
				game.emit(new AttackLockedTargetSkillsCheckEvent(this.source, this.target, this));
				break;
			case TARGET_LOCKED_SOURCE_WEAPON_ABILITIES:
				this.nextStage();
				game.emit(new AttackLockedSourceWeaponAbilitiesCheckEvent(this.source, this.target, this));
				break;
			case TARGET_LOCKED_TARGET_EQUIPMENT_ABILITIES:
				this.nextStage();
				game.emit(new AttackTargetEquipmentCheckEvent(this.target.getPlayerInfo(), this.attack, this));
				break;
			case DODGE:
				game.pushGameController(this.dodgeController);
				break;
			case ATTACK_DODGED_SOURCE_WEAPON_ABILITIES:
				// by default, an Attack does not apply damage if dodged
				this.setStage(AttackResolutionStage.END);
				game.emit(new AttackOnDodgedWeaponAbilitiesCheckEvent(this.source, this.target, this));
				break;
			case PRE_DAMAGE_SOURCE_WEAPON_ABILITIES:
				this.nextStage();
				game.emit(new AttackPreDamageWeaponAbilitiesCheckEvent(this.source, this.target, this));
				break;
			case PRE_DAMAGE_SOURCE_WEAPON_DAMAGE_MODIFIERS:
				this.nextStage();
				game.emit(new AttackDamageModifierEvent(this.damage));
				break;
			case APPLY_DAMAGE:
				this.nextStage();
				game.pushGameController(this.damageController);
				break;
			case END:
				break;
		default:
			break;
		}
	}

	@Override
	public void onDodged() {
		this.setStage(AttackResolutionStage.ATTACK_DODGED_SOURCE_WEAPON_ABILITIES);
	}

	@Override
	public void onNotDodged() {
		this.setStage(AttackResolutionStage.PRE_DAMAGE_SOURCE_WEAPON_ABILITIES);
	}
	
	public void skipStage(AttackResolutionStage stage) {
		this.skippedStages.add(stage);
	}
	
	public Attack getAttackCard() {
		return this.attack;
	}

	@Override
	protected AttackResolutionStage getInitialStage() {
		return AttackResolutionStage.TARGET_LOCKED_TARGET_HERO_SKILLS;
	}
	
}
