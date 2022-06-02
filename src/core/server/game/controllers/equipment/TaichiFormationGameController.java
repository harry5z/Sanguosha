package core.server.game.controllers.equipment;

import cards.Card;
import cards.Card.Color;
import commands.game.client.DecisionUIClientCommand;
import core.player.PlayerCompleteServer;
import core.server.game.BattleLog;
import core.server.game.GameInternal;
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

	public TaichiFormationGameController(DodgeGameController nextController, PlayerCompleteServer target) {
		this.target = target;
		this.nextController = nextController;
		this.confirmed = false;
		this.effective = false;
	}
	
	@Override
	protected void handleDecisionRequest(GameInternal game) throws GameFlowInterruptedException {
		throw new GameFlowInterruptedException(
			new DecisionUIClientCommand(
				this.target.getPlayerInfo(),
				"Use Taichi Formation?"
			)
		);		
	}

	@Override
	protected void handleDecisionConfirmation(GameInternal game) throws GameFlowInterruptedException {
		if (this.confirmed) {
			game.pushGameController(new ArbitrationController(this, this.target));
			game.log(BattleLog.custom(BattleLog.formatPlayer(target) + " used Taichi Formation"));
		}		
	}

	@Override
	protected void handleAction(GameInternal game) throws GameFlowInterruptedException {
		if (this.effective) {
			this.nextController.onDodgeStageSkipped();
			game.log(BattleLog.custom("Taichi Formation is effective"));
		} else {
			this.nextController.onDodgeStageNotSkipped();
			game.log(BattleLog.custom("Taichi Formation is not effective"));
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
