package core.server.game.controllers.mechanics;

import java.util.HashSet;
import java.util.Set;

import core.event.game.damage.TargetEquipmentCheckDamageEvent;
import core.player.PlayerCompleteServer;
import core.server.game.Damage;
import core.server.game.Damage.Element;
import core.server.game.controllers.AbstractGameController;
import core.server.game.controllers.GameControllerStage;
import core.server.game.Game;
import exceptions.server.game.GameFlowInterruptedException;

public class DamageGameController extends AbstractGameController<DamageGameController.DamageStage> {
	
	public static enum DamageStage implements GameControllerStage<DamageStage> {
		TARGET_HERO_SKILLS,
		TARGET_EQUIPMENT_ABILITIES,
		TARGET_DAMAGE,
		SOURCE_HERO_SKILLS_AFTER_DAMAGE,
		SKILLS_AFTER_DAMAGE,
		TARGET_CHAINED_DAMAGE,
		END;
	}
	
	private final Damage originalDamage;
	private final Damage damage;
	private final Set<DamageStage> skippedStages;

	public DamageGameController(Damage damage, Game game) {
		super(game);
		this.damage = damage;
		this.originalDamage = damage.clone();
		this.skippedStages = new HashSet<>();
	}

	@Override
	protected void handleStage(DamageStage stage) throws GameFlowInterruptedException {
		if (this.skippedStages.contains(stage)) {
			this.nextStage();
			return;
		}
		switch (stage) {
			case TARGET_HERO_SKILLS:
				// nothing here yet
				this.nextStage();
				break;
			case TARGET_EQUIPMENT_ABILITIES:
				this.nextStage();
				this.game.emit(new TargetEquipmentCheckDamageEvent(damage));
				break;
			case TARGET_DAMAGE:
				this.nextStage();
				this.damage.apply();
				break;
			case SOURCE_HERO_SKILLS_AFTER_DAMAGE:
				// nothing here yet
				this.nextStage();
				break;
			case SKILLS_AFTER_DAMAGE:
				// nothing here yet
				this.nextStage();
				break;
			case TARGET_CHAINED_DAMAGE:
				this.nextStage();
				PlayerCompleteServer target = this.originalDamage.getTarget();
				// pass down damage too all chained players if damage type is not NORMAL
				if (this.originalDamage.getElement() != Element.NORMAL && target.isChained()) {
					target.setChained(false);
					for (PlayerCompleteServer next = this.game.getNextPlayerAlive(target); next != target; next = this.game.getNextPlayerAlive(next)) {
						if (next.isChained()) {
							// chained players always take damage based on the initial damage value
							int damageAmount = this.originalDamage.isChainedDamage() ? this.originalDamage.getAmount() : this.damage.getAmount();
							Damage newDamage = new Damage(damageAmount, this.originalDamage.getElement(), this.originalDamage.getSource(), next);
							newDamage.setIsChainedDamage(true);
							this.game.pushGameController(new DamageGameController(newDamage, this.game));
							return;
						}
					}
				}
				break;
			case END:
				break;
		}
	}
	
	public void skipStage(DamageStage stage) {
		this.skippedStages.add(stage);
	}

	@Override
	protected DamageStage getInitialStage() {
		return DamageStage.TARGET_HERO_SKILLS;
	}

}
