package listeners.server;

import java.util.Set;
import java.util.stream.Collectors;

import commands.game.client.GameClientCommand;
import commands.game.client.UpdateOtherPlayerEquipmentGameClientCommand;
import listeners.game.HealthListener;
import net.Connection;
import net.client.ClientUI;
import net.server.GameRoom;
import ui.game.GamePanelUI;

public class ServerInGameHealthListener extends ServerInGamePlayerListener implements HealthListener {

	public ServerInGameHealthListener(String name, Set<String> allNames, GameRoom room) {
		super(name, allNames, room);
	}
	@Override
	public void onSetHealthLimit(int limit) {
		room.sendCommandToPlayer(
			name, 
			(ui, connection) -> ui.<GamePanelUI>getPanel().getContent().getLifebar().onSetHealthLimit(limit)
		);
		room.sendCommandToPlayers(
			otherNames.stream().collect(
				Collectors.toMap(
					n -> n, 
					n -> ((ui, connection) -> ui.<GamePanelUI>getPanel().getContent().getPlayer(name).changeHealthLimitTo(limit))
				)
			)
		);
	}

	@Override
	public void onSetHealthCurrent(int current) {
		room.sendCommandToPlayer(
			name, 
			(ui, connection) -> ui.<GamePanelUI>getPanel().getContent().getLifebar().onSetHealthCurrent(current)
		);
		room.sendCommandToPlayers(
			otherNames.stream().collect(
				Collectors.toMap(
					n -> n, 
					n -> ((ui, connection) -> ui.<GamePanelUI>getPanel().getContent().getPlayer(name).changeHealthCurrentTo(current))
				)
			)
		);
	}

	@Override
	public void onHealthChangedBy(int amount) {
		room.sendCommandToPlayer(
			name, 
			(ui, connection) -> ui.<GamePanelUI>getPanel().getContent().getLifebar().onHealthChangedBy(amount)
		);
		room.sendCommandToPlayers(
			otherNames.stream().collect(
				Collectors.toMap(
					n -> n, 
					n -> ((ui, connection) -> ui.<GamePanelUI>getPanel().getContent().getPlayer(name).changeHealthCurrentBy(amount))
				)
			)
		);		
	}

	@Override
	public void onDeath() {
		room.sendCommandToPlayer(
			name, 
			(ui, connection) -> ui.<GamePanelUI>getPanel().getContent().getLifebar().onDeath()
		);
		room.sendCommandToPlayers(
			otherNames.stream().collect(
				Collectors.toMap(
					n -> n, 
					n -> ((ui, connection) -> ui.<GamePanelUI>getPanel().getContent().getPlayer(name).kill())
				)
			)
		);
	}

}
