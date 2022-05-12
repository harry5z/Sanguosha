package commands.game.server.ingame;

import java.util.Set;

import cards.Card;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractSingleStageGameController;
import core.server.game.controllers.AttackUsableGameController;
import core.server.game.controllers.GameController;
import core.server.game.controllers.mechanics.UseCardOnHandGameController;
import exceptions.server.game.GameFlowInterruptedException;

public class AttackReactionInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 1L;

	private final PlayerInfo source;
	private final Card attack;
	
	public AttackReactionInGameServerCommand(PlayerInfo source, Card card) {
		this.source = source;
		this.attack = card;
	}
	
	@Override
	public GameController getGameController() {
		return new AbstractSingleStageGameController() {
			
			@Override
			protected void handleOnce(GameInternal game) throws GameFlowInterruptedException {
				PlayerCompleteServer user = game.findPlayer(source);
				if (attack != null) {
					game.<AttackUsableGameController>getNextGameController().onAttackUsed(game, attack);
					game.pushGameController(new UseCardOnHandGameController(user, Set.of(attack)));
				} else {
					game.<AttackUsableGameController>getNextGameController().onAttackNotUsed(game);
				}				
			}
		};

	}

}
