package commands.game.server.ingame;

import java.util.LinkedList;
import java.util.Queue;

import cards.Card;
import core.player.PlayerCompleteServer;
import core.server.game.controllers.GameController;
import core.server.game.controllers.specials.instants.BrotherhoodGameController;

public class InitiateBrotherhoodInGameServerCommand extends AbstractAOEInstantInitiationInGameServerCommand {

	private static final long serialVersionUID = 1L;
	
	public InitiateBrotherhoodInGameServerCommand(Card card) {
		super(card);
	}
	
	@Override
	protected GameController getAOEInitiationGameController(PlayerCompleteServer self, Queue<PlayerCompleteServer> others) {
		Queue<PlayerCompleteServer> queue = new LinkedList<>();
		queue.add(self);
		queue.addAll(others);
		return new BrotherhoodGameController(self, queue);
	}

}
