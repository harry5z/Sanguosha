package core.server.game.controllers;

import core.event.game.damage.TargetEquipmentCheckDamageEvent;
import core.server.game.Damage;
import core.server.game.Game;
import exceptions.server.game.GameFlowInterruptedException;
import utils.EnumWithNextStage;

public class DamageGameController extends AbstractGameController {
	
	public static enum DamageStage implements EnumWithNextStage<DamageStage> {
		TARGET_HERO_SKILLS,
		TARGET_EQUIPMENT_SKILLS,
		TARGET_CHECK_CHAINED,
		TARGET_DAMAGE,
		SOURCE_HERO_SKILLS_AFTER_DAMAGE,
		SKILLS_AFTER_DAMAGE,
		END;
	}
	
	private final Damage damage;
	private DamageStage stage;
	
	public DamageGameController(Damage damage, Game game) {
		super(game);
		this.damage = damage;
		this.stage = DamageStage.TARGET_HERO_SKILLS;
	}

	@Override
	public void proceed() {
		while (true) {
			switch (stage) {
				case TARGET_HERO_SKILLS:
					// nothing here yet
					stage = stage.nextStage();
					continue;
				case TARGET_EQUIPMENT_SKILLS:
					try {
						this.game.emit(new TargetEquipmentCheckDamageEvent(damage));
					} catch (GameFlowInterruptedException e) {
						// Do nothing
					}
					stage = stage.nextStage();
					continue;
				case TARGET_CHECK_CHAINED:
					// nothing here yet
					stage = stage.nextStage();
					continue;
				case TARGET_DAMAGE:
					damage.apply();
					stage = stage.nextStage();
					continue;
				case SOURCE_HERO_SKILLS_AFTER_DAMAGE:
					// nothing here yet
					stage = stage.nextStage();
					continue;
				case SKILLS_AFTER_DAMAGE:
					// nothing here yet
					stage = stage.nextStage();
					continue;
				case END:
					this.onCompleted();
					return;
				default:
					return;
			}
		}
	}

}
