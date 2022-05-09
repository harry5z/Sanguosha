package core.server.game.controllers.equipment;

import cards.Card;
import cards.Card.Color;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import core.server.game.controllers.AbstractStagelessGameController;
import core.server.game.controllers.ArbitrationRequiredGameController;
import core.server.game.controllers.DecisionRequiredGameController;
import core.server.game.controllers.mechanics.ArbitrationController;
import core.server.game.controllers.mechanics.DodgeGameController;

public class TaichiFormationGameController extends AbstractStagelessGameController implements DecisionRequiredGameController, ArbitrationRequiredGameController {
	
	private final PlayerCompleteServer target;
	private final DodgeGameController nextController;
	private boolean effective;

	public TaichiFormationGameController(Game game, DodgeGameController nextController, PlayerCompleteServer target) {
		super(game);
		this.target = target;
		this.nextController = nextController;
		this.effective = false;
	}

	@Override
	public void proceed() {
		if (this.effective) {
			this.nextController.onDodgeStageSkipped();
		} else {
			this.nextController.onDodgeStageNotSkipped();
		}
		this.onUnloaded();
		this.game.getGameController().proceed();
	}

	@Override
	public void onDecisionMade(boolean confirmed) {
		if (confirmed) {
			this.game.pushGameController(new ArbitrationController(this.game, this, this.target));
		}
	}

	@Override
	public void onArbitrationCompleted(Card card) {
		this.effective = card.getColor() == Color.RED;
	}

}
