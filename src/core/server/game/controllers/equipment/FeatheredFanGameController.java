package core.server.game.controllers.equipment;

import cards.basics.Attack;
import commands.client.game.DecisionUIClientCommand;
import core.player.PlayerCompleteServer;
import core.server.game.Damage.Element;
import core.server.game.BattleLog;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractPlayerDecisionActionGameController;
import core.server.game.controllers.DecisionRequiredGameController;
import core.server.game.controllers.mechanics.AttackGameController;
import exceptions.server.game.GameFlowInterruptedException;

public class FeatheredFanGameController extends AbstractPlayerDecisionActionGameController implements DecisionRequiredGameController {
	
	private final AttackGameController attackController;
	private final PlayerCompleteServer source;
	private boolean confirmed;

	public FeatheredFanGameController(PlayerCompleteServer source, AttackGameController attackController) {
		this.source = source;
		this.attackController = attackController;
		this.confirmed = false;
	}

	@Override
	public void onDecisionMade(boolean confirmed) {
		this.confirmed = confirmed;
	}

	@Override
	protected void handleDecisionRequest(GameInternal game) throws GameFlowInterruptedException {
		throw new GameFlowInterruptedException(
			new DecisionUIClientCommand(this.source.getPlayerInfo(), "Use Feathered Fan?")
		);		
	}

	@Override
	protected void handleDecisionConfirmation(GameInternal game) throws GameFlowInterruptedException {
		if (this.confirmed) {
			Attack original = this.attackController.getAttackCard();
			this.attackController.setAttackCard(new Attack(Element.FIRE, original.getNumber(), original.getSuit()));
			game.log(BattleLog
				.playerAUsedEquipment(source, source.getWeapon())
				.to("add Fire Element to the Attack")
			);
		}		
	}

	@Override
	protected void handleAction(GameInternal game) throws GameFlowInterruptedException {
		// No action
	}

}
