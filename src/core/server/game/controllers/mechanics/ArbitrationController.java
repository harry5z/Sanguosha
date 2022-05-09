package core.server.game.controllers.mechanics;

import java.util.Set;

import cards.Card;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import core.server.game.controllers.AbstractGameController;
import core.server.game.controllers.ArbitrationRequiredGameController;
import core.server.game.controllers.GameControllerStage;

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

	public ArbitrationController(Game game, ArbitrationRequiredGameController nextController, PlayerCompleteServer target) {
		super(game);
		this.nextController = nextController;
		this.arbitrationCard = null;
		this.target = target;
	}

	@Override
	public void proceed() {
		switch (this.stage) {
			case ARBITRATION:
				this.arbitrationCard = this.game.getDeck().draw();
				this.nextController.onArbitrationCompleted(this.arbitrationCard);
				this.stage = this.stage.nextStage();
				this.proceed();
				break;
			case POST_ARBITRATION_SKILLS:
				this.stage = this.stage.nextStage();
				this.game.getGameController().proceed();
				break;
			case ARBITRATION_CARD_DISPOSAL:
				this.stage = this.stage.nextStage();
				this.game.pushGameController(new RecycleCardsGameController(
					this.game,
					this.target,
					Set.of(this.arbitrationCard)
				));
				this.game.getGameController().proceed();
				break;
			case END:
				this.onUnloaded();
				this.game.getGameController().proceed();
				break;
		}
	}

	@Override
	protected ArbitrationStage getInitialStage() {
		return ArbitrationStage.ARBITRATION;
	}
	
}
