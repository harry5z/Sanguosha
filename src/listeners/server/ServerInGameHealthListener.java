package listeners.server;

import java.util.Set;
import java.util.stream.Collectors;

import listeners.game.HealthListener;
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
			(ui, connection) -> {
				synchronized (ui) {
					ui.<GamePanelUI>getPanel().getContent().getSelf().changeHealthCurrentTo(limit);
				}
			}
		);
		final String playerName = name; // To avoid referencing "this" while serializing
		room.sendCommandToPlayers(
			otherNames.stream().collect(
				Collectors.toMap(
					n -> n, 
					n -> ((ui, connection) -> {
						synchronized (ui) {
							ui.<GamePanelUI>getPanel().getContent().getPlayer(playerName).changeHealthLimitTo(limit);
						}
					})
				)
			)
		);
	}

	@Override
	public void onSetHealthCurrent(int current) {
		room.sendCommandToPlayer(
			name, 
			(ui, connection) -> {
				synchronized (ui) {
					ui.<GamePanelUI>getPanel().getContent().getSelf().changeHealthCurrentTo(current);
				}
			}
		);
		final String playerName = name; // To avoid referencing "this" while serializing
		room.sendCommandToPlayers(
			otherNames.stream().collect(
				Collectors.toMap(
					n -> n, 
					n -> ((ui, connection) -> {
						synchronized (ui) {
							ui.<GamePanelUI>getPanel().getContent().getPlayer(playerName).changeHealthCurrentTo(current);
						}
					})
				)
			)
		);
	}

	@Override
	public void onHealthChangedBy(int amount) {
		room.sendCommandToPlayer(
			name, 
			(ui, connection) -> {
				synchronized (ui) {
					ui.<GamePanelUI>getPanel().getContent().getSelf().changeHealthCurrentBy(amount);
				}
			}
		);
		final String playerName = name; // To avoid referencing "this" while serializing
		room.sendCommandToPlayers(
			otherNames.stream().collect(
				Collectors.toMap(
					n -> n, 
					n -> ((ui, connection) -> {
						synchronized (ui) {
							ui.<GamePanelUI>getPanel().getContent().getPlayer(playerName).changeHealthCurrentBy(amount);
						}
					})
				)
			)
		);		
	}

	@Override
	public void onDeath() {
		room.sendCommandToPlayer(
			name, 
			(ui, connection) -> {
				synchronized (ui) {
					ui.<GamePanelUI>getPanel().getContent().getSelf().kill();
				}
			}
		);
		final String playerName = name; // To avoid referencing "this" while serializing
		room.sendCommandToPlayers(
			otherNames.stream().collect(
				Collectors.toMap(
					n -> n, 
					n -> ((ui, connection) -> {
						synchronized (ui) {
							ui.<GamePanelUI>getPanel().getContent().getPlayer(playerName).kill();
						}
					})
				)
			)
		);
	}

}
