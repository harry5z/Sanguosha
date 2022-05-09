package commands.game.server.ingame;

import java.util.Set;

import cards.Card;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.game.Game;
import core.server.game.controllers.AttackUsableGameController;
import core.server.game.controllers.mechanics.UseCardOnHandGameController;

public class AttackReactionInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 1L;

	private final PlayerInfo source;
	private final Card attack;
	
	public AttackReactionInGameServerCommand(PlayerInfo source, Card card) {
		this.source = source;
		this.attack = card;
	}
	
	@Override
	public void execute(Game game) {
		PlayerCompleteServer user = game.findPlayer(this.source);
		if (this.attack != null) {
			game.<AttackUsableGameController>getGameController().onAttackUsed(this.attack);
			game.pushGameController(new UseCardOnHandGameController(game, user, Set.of(this.attack)));
		} else {
			game.<AttackUsableGameController>getGameController().onAttackNotUsed();
		}
		game.getGameController().proceed();
	}

}
