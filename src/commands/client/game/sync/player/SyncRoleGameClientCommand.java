package commands.client.game.sync.player;

import core.GameState;
import core.player.Role;
import exceptions.server.game.InvalidPlayerCommandException;

public class SyncRoleGameClientCommand extends AbstractSyncPlayerClientCommand {

	private static final long serialVersionUID = 1L;
	
	private final String target;
	private final Role role;
	
	public SyncRoleGameClientCommand(String target, Role role) {
		this.target = target;
		this.role = role;
	}

	@Override
	protected void sync(GameState state) throws InvalidPlayerCommandException {
		if (state.getSelf().getName().equals(target)) {
			state.getSelf().setRole(role);
		} else {
			state.getPlayer(target).setRole(role);
		}
	}

}
