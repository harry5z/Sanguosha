package core.server.game.controllers;

public class AttackGameController implements GameController {

	public enum AttackStage {
		TARGET_SELECTION, // client side
		BEFORE_TARGET_LOCKED, // client side
		TARGET_LOCKED,
		AFTER_TARGET_LOCKED_SKILLS,
		AFTER_TARGET_LOCKED_WEAPONS,
		DODGE_DECISION,
		USING_DODGE,
		AFTER_USING_DODGE,
		ATTACK_DODGED_SKILLS,
		ATTACK_DODGED_WEAPONS,
		ATTACK_NOT_DODGED_PREVENTION,
		ATTACK_NOT_DODGED_ADDITION,
		BEFORE_DAMAGE,
		DAMAGE,
		END;
		
		private static final AttackStage[] VALUES = values();
		
		public AttackStage nextStage() {
			return VALUES[(this.ordinal() + 1) % VALUES.length];
		}
	}
	
	private AttackStage stage;
	
	public AttackGameController() {
		this.stage = AttackStage.TARGET_LOCKED;
	}

	@Override
	public void proceed() {
		
	}

}
