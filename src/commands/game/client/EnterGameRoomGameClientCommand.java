package commands.game.client;

import java.util.List;

import core.PlayerInfo;
import net.Connection;
import net.client.ClientUI;
import net.client.GamePanel;

public class EnterGameRoomGameClientCommand implements GameClientCommand {
	private static final long serialVersionUID = 1094417892948875381L;
	private final List<PlayerInfo> players;
	private final PlayerInfo self;
	
	public EnterGameRoomGameClientCommand(List<PlayerInfo> players, PlayerInfo self) {
		this.players = players;
		this.self = self;
	}
	@Override
	public void execute(ClientUI ui, Connection connection) {
		ui.onNewPanelDisplayed(new GamePanel(self, players, connection));
	}

}
