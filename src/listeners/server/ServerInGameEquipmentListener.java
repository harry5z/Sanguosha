package listeners.server;

import java.util.Set;
import java.util.stream.Collectors;

import listeners.game.EquipmentListener;
import net.server.GameRoom;
import ui.game.GamePanelUI;
import cards.equipments.Equipment;
import cards.equipments.Equipment.EquipmentType;

import commands.game.client.RemoveOtherPlayerEquipmentGameClientCommand;
import commands.game.client.RemovePlayerEquipmentGameClientCommand;
import commands.game.client.UpdateOtherPlayerEquipmentGameClientCommand;
import commands.game.client.UpdatePlayerEquipmentGameClientCommand;

public class ServerInGameEquipmentListener extends ServerInGamePlayerListener implements EquipmentListener {

	public ServerInGameEquipmentListener(String name, Set<String> allNames, GameRoom room) {
		super(name, allNames, room);
	}
	
	@Override
	public void onEquipped(Equipment equipment) {
		room.sendCommandToPlayer(
			name, 
			(ui, connection) -> ui.<GamePanelUI>getPanel().getContent().getEquipmentRackUI().onEquipped(equipment)
		);
		room.sendCommandToPlayers(
			otherNames.stream().collect(
				Collectors.toMap(
					n -> n, 
					n -> ((ui, connection) -> ui.<GamePanelUI>getPanel().getContent().getPlayer(name).equip(equipment))
				)
			)
		);
	}

	@Override
	public void onUnequipped(EquipmentType type) {
		room.sendCommandToPlayer(
			name,
			(ui, connection) -> ui.<GamePanelUI>getPanel().getContent().getEquipmentRackUI().onUnequipped(type)
		);
		room.sendCommandToPlayers(
			otherNames.stream().collect(
				Collectors.toMap(
					n -> n, 
					n -> ((ui, connection) -> ui.<GamePanelUI>getPanel().getContent().getPlayer(name).unequip(type))
				)
			)
		);
	}

}
