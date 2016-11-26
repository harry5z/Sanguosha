package core.server.game.controllers.specials;

import core.server.game.controllers.GameController;

public interface SpecialGameController extends GameController {
	
	public static enum SpecialStage {
		TARGET_LOCKED,
		NEUTRALIZATION,
		EFFECT,
		EFFECT_TAKEN;
		
		private static final SpecialStage[] VALUES = values();
		
		public SpecialStage nextStage() {
			return VALUES[(this.ordinal() + 1) % VALUES.length];
		}
	}
	
	public void onNeutralized();
	
	public void onNeutralizationCanceled();

}
