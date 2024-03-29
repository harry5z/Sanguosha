package core.server.game.controllers.equipment;

import cards.Card;
import cards.basics.Attack;
import commands.client.game.RequestAttackGameUIClientCommand;
import core.player.PlayerCompleteServer;
import core.server.game.BattleLog;
import core.server.game.GameInternal;
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
	protected void handleDecisionRequest(GameInternal game) throws GameFlowInterruptedException {
		throw new GameFlowInterruptedException(
			new RequestAttackGameUIClientCommand(source, "Dragon Blade: Use another Attack?")
		);
	}

	@Override
	protected void handleDecisionConfirmation(GameInternal game) throws GameFlowInterruptedException {
		if (this.actionTaken) {
			game.pushGameController(new AttackGameController(this.source, this.target, (Attack) card));
			game.log(BattleLog
				.playerAUsedEquipment(source, source.getWeapon())
				.withCard(card)
				.to("re-initiate Attack")
				.onPlayer(target)
			);
		}
	}

	@Override
	protected void handleAction(GameInternal game) throws GameFlowInterruptedException {
		// Action is done in decision confirmation
	}

	@Override
	public void onAttackUsed(GameInternal game, Card card) {
		this.actionTaken = true;
		this.card = card;
	}

	@Override
	public void onAttackNotUsed(GameInternal game) {
		this.actionTaken = false;
	}

}
