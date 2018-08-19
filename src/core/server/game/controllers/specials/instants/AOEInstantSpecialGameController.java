package core.server.game.controllers.specials.instants;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Supplier;

import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.game.Game;

public abstract class AOEInstantSpecialGameController extends MultiTargetInstantSpecialGameController {
	
	public AOEInstantSpecialGameController(PlayerInfo source, Game game, boolean includeSelf) {
		super(
			source,
			game, 
			(new Supplier<Queue<PlayerInfo>>() {
				@Override
				public Queue<PlayerInfo> get() {
					Queue<PlayerInfo> queue = new LinkedList<>();
					PlayerCompleteServer currentPlayer = game.getCurrentPlayer();
					PlayerCompleteServer next = game.getNextPlayerAlive(currentPlayer);
					if (includeSelf) {
						queue.add(currentPlayer.getPlayerInfo());
					}
					while (next != currentPlayer) {
						queue.add(next.getPlayerInfo());
						next = game.getNextPlayerAlive(next);
					}
					return queue;
				}
				
			}).get()
		);
	}

}
