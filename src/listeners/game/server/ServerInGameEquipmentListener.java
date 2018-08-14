package listeners.game.server;

import java.util.Set;

import cards.equipments.Equipment;
import cards.equipments.Equipment.EquipmentType;
import commands.game.client.sync.SyncCommandsUtil;
import commands.game.client.sync.equipment.SyncEquipGameUIClientCommand;
import commands.game.client.sync.equipment.SyncUnequipGameUIClientCommand;
import core.server.GameRoom;
import listeners.game.EquipmentListener;

public class ServerInGameEquipmentListener extends ServerInGamePlayerListener implements EquipmentListener {
	
	public ServerInGameEquipmentListener(String name, Set<String> allNames, GameRoom room) {
		super(name, allNames, room);
	}
	
	@Override
	public void onEquipped(Equipment equipment) {
		room.sendCommandToPlayers(
			SyncCommandsUtil.generateMapForSameCommand(
				name, 
				otherNames, 
				new SyncEquipGameUIClientCommand(name, equipment)
			)
		);
	}

	@Override
	public void onUnequipped(EquipmentType type) {
		room.sendCommandToPlayers(
			SyncCommandsUtil.generateMapForSameCommand(
				name, 
				otherNames, 
				new SyncUnequipGameUIClientCommand(name, type)
			)	
		);
	}

}
