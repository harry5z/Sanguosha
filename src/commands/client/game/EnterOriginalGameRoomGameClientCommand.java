package commands.client.game;

import java.util.List;

import commands.client.game.sync.SyncGameClientCommand;
import core.client.ClientFrame;
import core.client.GamePanelOriginal;
import core.player.PlayerInfo;
import net.client.ClientConnection;

public class EnterOriginalGameRoomGameClientCommand implements SyncGameClientCommand {
	
	private static final long serialVersionUID = 1094417892948875381L;
	
	private final List<PlayerInfo> players;
	private final PlayerInfo self;
	
	public EnterOriginalGameRoomGameClientCommand(List<PlayerInfo> players, PlayerInfo self) {
		this.players = players;
		this.self = self;
	}
	@Override
	public void execute(ClientFrame frame, ClientConnection connection) {
		synchronized (frame) {
			frame.onNewPanelDisplayed(new GamePanelOriginal(self, players, connection));
		}
	}
	
}
