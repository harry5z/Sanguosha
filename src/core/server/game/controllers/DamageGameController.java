package core.server.game.controllers;

import cards.equipments.Equipment.EquipmentType;
import core.server.game.Damage;
import core.server.game.Game;

public class DamageGameController implements GameController {
	
	public static enum DamageStage {
		TARGET_HERO_SKILLS,
		TARGET_EQUIPMENT_SKILLS,
		TARGET_CHECK_CHAINED,
		TARGET_DAMAGE,
		SOURCE_HERO_SKILLS_AFTER_DAMAGE,
		SKILLS_AFTER_DAMAGE,
		END;

		private static final DamageStage[] VALUES = values();
		
		public DamageStage nextStage() {
			return VALUES[(this.ordinal() + 1) % VALUES.length];
		}
	}
	
	private final Damage damage;
	private final Game game;
	private DamageStage stage;
	
	public DamageGameController(Damage damage, Game game) {
		this.damage = damage;
		this.game = game;
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
					if (damage.getTarget().isEquipped(EquipmentType.SHIELD)) {
						damage.getTarget().getShield().modifyDamage(damage);
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
					game.popGameController();
					game.getGameController().proceed();
					return;
				default:
					return;
			}
		}
	}

}
