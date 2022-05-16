package commands.game.client.sync.status;

import java.util.Set;
import java.util.stream.Collectors;

import commands.game.client.sync.AbstractSyncGameUIClientCommand;
import core.client.GamePanel;
import core.player.PlayerInfo;
import exceptions.server.game.InvalidPlayerCommandException;

public class SyncAttackUsedGameUIClientCommand extends AbstractSyncGameUIClientCommand {

	private static final long serialVersionUID = 7306926860771153864L;
	
	private final Set<PlayerInfo> targets;
	
	public SyncAttackUsedGameUIClientCommand(Set<PlayerInfo> targets) {
		this.targets = targets;
	}
	

	@Override
	protected void execute(GamePanel panel) {
		try {
			panel.getGameState().getSelf().useAttack(
				this.targets.stream().map(info -> panel.getGameState().getPlayer(info.getName())).collect(Collectors.toSet())
			);
		} catch (InvalidPlayerCommandException e) {
			e.printStackTrace();
		}
	}

}
