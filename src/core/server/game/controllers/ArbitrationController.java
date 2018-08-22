package core.server.game.controllers;

import java.util.Set;

import cards.Card;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import core.server.game.controllers.interfaces.ArbitrationRequiredGameController;
import utils.EnumWithNextStage;

public class ArbitrationController extends AbstractGameController {
	
	public static enum ArbitrationStage implements EnumWithNextStage<ArbitrationStage> {
		ARBITRATION,
		POST_ARBITRATION_SKILLS,
		ARBITRATION_CARD_DISPOSAL,
		END;
	}
	
	private Card arbitrationCard;
	private PlayerCompleteServer target;
	private ArbitrationStage stage;

	public ArbitrationController(Game game, PlayerCompleteServer target) {
		super(game);
		this.arbitrationCard = null;
		this.target = target;
		this.stage = ArbitrationStage.ARBITRATION;
	}

	@Override
	public void proceed() {
		switch (this.stage) {
			case ARBITRATION:
				this.arbitrationCard = this.game.getDeck().draw();
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
	protected void onNextControllerLoaded(GameController controller) {
		((ArbitrationRequiredGameController) controller).onArbitrationCompleted(this.arbitrationCard);
	}

}
