package commands.game.client.sync.player;

import java.util.Set;
import java.util.stream.Collectors;

import core.GameState;
import core.player.PlayerInfo;
import exceptions.server.game.InvalidPlayerCommandException;

public class SyncAttackUsedGameClientCommand extends AbstractSyncPlayerClientCommand {

	private static final long serialVersionUID = 7306926860771153864L;
	
	private final Set<PlayerInfo> targets;
	
	public SyncAttackUsedGameClientCommand(Set<PlayerInfo> targets) {
		this.targets = targets;
	}
	

	@Override
	protected void sync(GameState state) throws InvalidPlayerCommandException {
		state.getSelf().useAttack(
			this.targets.stream().map(info -> state.getPlayer(info.getName())).collect(Collectors.toSet())
		);
	}

}
