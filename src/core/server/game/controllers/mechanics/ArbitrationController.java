package core.server.game.controllers.mechanics;

import java.util.Set;

import cards.Card;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import core.server.game.controllers.AbstractGameController;
import core.server.game.controllers.ArbitrationRequiredGameController;
import core.server.game.controllers.GameControllerStage;
import exceptions.server.game.GameFlowInterruptedException;

public class ArbitrationController extends AbstractGameController<ArbitrationController.ArbitrationStage> {
	
	public static enum ArbitrationStage implements GameControllerStage<ArbitrationStage> {
		ARBITRATION,
		POST_ARBITRATION_SKILLS,
		ARBITRATION_CARD_DISPOSAL,
		END;
	}
	
	private final ArbitrationRequiredGameController nextController;
	private Card arbitrationCard;
	private PlayerCompleteServer target;

	public ArbitrationController(ArbitrationRequiredGameController nextController, PlayerCompleteServer target) {
		this.nextController = nextController;
		this.arbitrationCard = null;
		this.target = target;
	}
	
	@Override
	protected void handleStage(Game game, ArbitrationStage stage) throws GameFlowInterruptedException {
		switch (stage) {
			case ARBITRATION:
				this.nextStage();
				this.arbitrationCard = game.getDeck().draw();
				this.nextController.onArbitrationCompleted(this.arbitrationCard);
				break;
			case POST_ARBITRATION_SKILLS:
				// Nothing yet
				this.nextStage();
				break;
			case ARBITRATION_CARD_DISPOSAL:
				this.nextStage();
				game.pushGameController(new RecycleCardsGameController(
					this.target,
					Set.of(this.arbitrationCard)
				));
				break;
			case END:
				break;
		}		
	}

	@Override
	protected ArbitrationStage getInitialStage() {
		return ArbitrationStage.ARBITRATION;
	}

}
