package core.server.game.controllers.skills;

import commands.game.client.DecisionUIClientCommand;
import core.heroes.skills.HuangZhongSharpshooterHeroSkill;
import core.player.PlayerCompleteServer;
import core.server.game.BattleLog;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractPlayerDecisionActionGameController;
import core.server.game.controllers.DecisionRequiredGameController;
import core.server.game.controllers.mechanics.AttackResolutionGameController;
import core.server.game.controllers.mechanics.AttackResolutionGameController.AttackResolutionStage;
import exceptions.server.game.GameFlowInterruptedException;

public class HuangZhongSharpshooterGameController extends AbstractPlayerDecisionActionGameController
	implements DecisionRequiredGameController {

	private final PlayerCompleteServer source;
	private final AttackResolutionGameController nextController;
	private boolean confirmed;

	public HuangZhongSharpshooterGameController(AttackResolutionGameController nextController, PlayerCompleteServer source) {
		this.source = source;
		this.nextController = nextController;
		this.confirmed = false;
	}
	
	@Override
	protected void handleDecisionRequest(GameInternal game) throws GameFlowInterruptedException {
		throw new GameFlowInterruptedException(
			new DecisionUIClientCommand(source.getPlayerInfo(), "Activate Sharpshooter (target cannot use dodge)?")
		);	
	}

	@Override
	protected void handleDecisionConfirmation(GameInternal game) throws GameFlowInterruptedException {
		if (confirmed) {
			nextController.skipStage(AttackResolutionStage.DODGE);
			game.log(BattleLog
				.playerAUsedSkill(source, new HuangZhongSharpshooterHeroSkill())
				.to("make the Attack hit")
			);
		}
	}

	@Override
	protected void handleAction(GameInternal game) throws GameFlowInterruptedException {
		// no action
	}
	
	@Override
	public void onDecisionMade(boolean confirmed) {
		this.confirmed = confirmed;
	}

}
