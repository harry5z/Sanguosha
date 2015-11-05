package commands.game;

import java.util.List;

import net.Connection;
import net.client.ClientUI;
import ui.PanelGui;
import core.PlayerInfo;

public class EnterGameRoomGameClientCommand implements GameClientCommand {
	private static final long serialVersionUID = -6206113314664014688L;
	private final List<PlayerInfo> players;
	private final PlayerInfo self;
	
	public EnterGameRoomGameClientCommand(List<PlayerInfo> players, PlayerInfo self) {
		this.players = players;
		this.self = self;
	}
	@Override
	public void execute(ClientUI ui, Connection connection) {
		PanelGui panel = new PanelGui(self);
		for (PlayerInfo player : players) {
			if (!player.equals(self)) {
				panel.addPlayer(player);
			}
		}
		ui.onNewPanelDisplayed(panel);
	}

}
