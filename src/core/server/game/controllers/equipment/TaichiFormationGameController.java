package core.server.game.controllers.equipment;

import cards.Card;
import cards.Card.Color;
import commands.game.client.DecisionUIClientCommand;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import core.server.game.controllers.AbstractPlayerDecisionActionGameController;
import core.server.game.controllers.ArbitrationRequiredGameController;
import core.server.game.controllers.DecisionRequiredGameController;
import core.server.game.controllers.mechanics.ArbitrationController;
import core.server.game.controllers.mechanics.DodgeGameController;
import exceptions.server.game.GameFlowInterruptedException;

public class TaichiFormationGameController
	extends AbstractPlayerDecisionActionGameController
	implements DecisionRequiredGameController, ArbitrationRequiredGameController {
	
	private final PlayerCompleteServer target;
	private final DodgeGameController nextController;
	private boolean confirmed;
	private boolean effective;

	public TaichiFormationGameController(Game game, DodgeGameController nextController, PlayerCompleteServer target) {
		super(game);
		this.target = target;
		this.nextController = nextController;
		this.confirmed = false;
		this.effective = false;
	}
	
	@Override
	protected void handleDecisionRequest() throws GameFlowInterruptedException {
		this.game.getConnectionController().sendCommandToAllPlayers(new DecisionUIClientCommand(
			this.target.getPlayerInfo(),
			"Use Taichi Formation?"
		));
		throw new GameFlowInterruptedException();		
	}

	@Override
	protected void handleDecisionConfirmation() throws GameFlowInterruptedException {
		if (this.confirmed) {
			this.game.pushGameController(new ArbitrationController(this.game, this, this.target));
		}		
	}

	@Override
	protected void handleAction() throws GameFlowInterruptedException {
		if (this.effective) {
			this.nextController.onDodgeStageSkipped();
		} else {
			this.nextController.onDodgeStageNotSkipped();
		}		
	}

	@Override
	public void onDecisionMade(boolean confirmed) {
		this.confirmed = confirmed;
	}

	@Override
	public void onArbitrationCompleted(Card card) {
		this.effective = card.getColor() == Color.RED;
	}

}
