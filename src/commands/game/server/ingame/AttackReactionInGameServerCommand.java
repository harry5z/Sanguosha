package commands.game.server.ingame;

import cards.Card;
import core.server.game.Game;
import core.server.game.controllers.interfaces.AttackUsableGameController;

public class AttackReactionInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 1L;

	private final Card attack;
	
	public AttackReactionInGameServerCommand(Card card) {
		this.attack = card;
	}
	
	@Override
	public void execute(Game game) {
		if (this.attack != null) {
			game.<AttackUsableGameController>getGameController().onAttackUsed(this.attack);
		} else {
			game.<AttackUsableGameController>getGameController().onAttackNotUsed();
		}
	}

}
