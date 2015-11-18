package listeners.server;

import java.util.Set;
import java.util.stream.Collectors;

import listeners.game.EquipmentListener;
import net.server.GameRoom;
import ui.game.GamePanelUI;
import cards.equipments.Equipment;
import cards.equipments.Equipment.EquipmentType;

public class ServerInGameEquipmentListener extends ServerInGamePlayerListener implements EquipmentListener {

	public ServerInGameEquipmentListener(String name, Set<String> allNames, GameRoom room) {
		super(name, allNames, room);
	}
	
	@Override
	public void onEquipped(Equipment equipment) {
		room.sendCommandToPlayer(
			name, 
			(ui, connection) -> {
				synchronized (ui) {
					ui.<GamePanelUI>getPanel().getContent().getSelf().equip(equipment);
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
							ui.<GamePanelUI>getPanel().getContent().getPlayer(playerName).equip(equipment);
						}
					})
				)
			)
		);
	}

	@Override
	public void onUnequipped(EquipmentType type) {
		room.sendCommandToPlayer(
			name,
			(ui, connection) -> {
				synchronized (ui) {
					ui.<GamePanelUI>getPanel().getContent().getSelf().unequip(type);
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
							ui.<GamePanelUI>getPanel().getContent().getPlayer(playerName).unequip(type);
						}
					})
				)
			)
		);
	}

}
