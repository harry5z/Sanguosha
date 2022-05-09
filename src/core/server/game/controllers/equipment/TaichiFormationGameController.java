package core.server.game.controllers.equipment;

import cards.Card;
import cards.Card.Color;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import core.server.game.controllers.AbstractGameController;
import core.server.game.controllers.ArbitrationRequiredGameController;
import core.server.game.controllers.DecisionRequiredGameController;
import core.server.game.controllers.GameController;
import core.server.game.controllers.mechanics.ArbitrationController;
import core.server.game.controllers.mechanics.DodgeGameController;

public class TaichiFormationGameController extends AbstractGameController implements DecisionRequiredGameController, ArbitrationRequiredGameController {
	
	private final PlayerCompleteServer target;
	private boolean effective;

	public TaichiFormationGameController(Game game, PlayerCompleteServer target) {
		super(game);
		this.target = target;
		this.effective = false;
	}

	@Override
	public void proceed() {
		this.onUnloaded();
		this.game.getGameController().proceed();
	}

	@Override
	public void onDecisionMade(boolean confirmed) {
		if (confirmed) {
			this.game.pushGameController(new ArbitrationController(this.game, this.target));
		}
	}

	@Override
	public void onArbitrationCompleted(Card card) {
		this.effective = card.getColor() == Color.RED;
	}
	
	@Override
	protected void onNextControllerLoaded(GameController controller) {
		if (this.effective) {
			((DodgeGameController) controller).onDodgeStageSkipped();
		} else {
			((DodgeGameController) controller).onDodgeStageNotSkipped();
		}
	}

}
