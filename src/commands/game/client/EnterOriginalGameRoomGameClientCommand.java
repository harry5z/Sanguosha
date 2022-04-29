package commands.game.client;

import java.util.List;

import core.client.ClientFrame;
import core.client.GamePanelOriginal;
import core.player.PlayerInfo;
import net.Connection;

public class EnterOriginalGameRoomGameClientCommand implements GameClientCommand {
	
	private static final long serialVersionUID = 1094417892948875381L;
	
	private final List<PlayerInfo> players;
	private final PlayerInfo self;
	
	public EnterOriginalGameRoomGameClientCommand(List<PlayerInfo> players, PlayerInfo self) {
		this.players = players;
		this.self = self;
	}
	@Override
	public void execute(ClientFrame ui, Connection connection) {
		ui.onNewPanelDisplayed(new GamePanelOriginal(self, players, connection));
	}

}
