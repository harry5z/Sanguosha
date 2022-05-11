package core.server.game.controllers.specials;

import core.server.game.controllers.GameControllerStage;
import core.server.game.controllers.GameController;

public interface SpecialGameController extends GameController {
	
	public static enum SpecialStage implements GameControllerStage<SpecialStage> {
		TARGET_LOCKED,
		NEUTRALIZATION,
		EFFECT,
		TARGET_SWITCH,
		END;
	}
	
	public void onNeutralized();
	
	public void onNeutralizationCanceled();

}
