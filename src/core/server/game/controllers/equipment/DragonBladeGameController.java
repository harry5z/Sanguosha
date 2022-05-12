package core.server.game.controllers.equipment;

import cards.Card;
import cards.basics.Attack;
import core.event.game.basic.RequestAttackEvent;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import core.server.game.controllers.AbstractPlayerDecisionActionGameController;
import core.server.game.controllers.AttackUsableGameController;
import core.server.game.controllers.mechanics.AttackGameController;
import exceptions.server.game.GameFlowInterruptedException;

public class DragonBladeGameController extends AbstractPlayerDecisionActionGameController implements AttackUsableGameController {
	
	private final PlayerCompleteServer source;
	private final PlayerCompleteServer target;
	private Card card;
	private boolean actionTaken;

	public DragonBladeGameController(PlayerCompleteServer source, PlayerCompleteServer target) {
		this.source = source;
		this.target = target;
		this.actionTaken = false;
	}

	@Override
	protected void handleDecisionRequest(Game game) throws GameFlowInterruptedException {
		game.emit(new RequestAttackEvent(
			this.source.getPlayerInfo(),
			"Use Dragon Blade?"
		));	
		throw new GameFlowInterruptedException();
	}

	@Override
	protected void handleDecisionConfirmation(Game game) throws GameFlowInterruptedException {
		if (this.actionTaken) {
			game.pushGameController(new AttackGameController(this.source, this.target, (Attack) card));
		}
	}

	@Override
	protected void handleAction(Game game) throws GameFlowInterruptedException {
		// Action is done in decision confirmation
	}

	@Override
	public void onAttackUsed(Game game, Card card) {
		this.actionTaken = true;
		this.card = card;
	}

	@Override
	public void onAttackNotUsed(Game game) {
		this.actionTaken = false;
	}

}
