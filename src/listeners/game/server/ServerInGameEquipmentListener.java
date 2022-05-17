package listeners.game.server;

import java.util.Set;

import cards.equipments.Equipment;
import cards.equipments.Equipment.EquipmentType;
import commands.game.client.sync.SyncCommandsUtil;
import commands.game.client.sync.equipment.SyncEquipGameUIClientCommand;
import commands.game.client.sync.equipment.SyncUnequipGameUIClientCommand;
import core.server.SyncController;
import listeners.game.EquipmentListener;

public class ServerInGameEquipmentListener extends ServerInGamePlayerListener implements EquipmentListener {
	
	public ServerInGameEquipmentListener(String name, Set<String> allNames, SyncController controller) {
		super(name, allNames, controller);
	}
	
	@Override
	public void onEquipped(Equipment equipment) {
		controller.sendSyncCommandToPlayers(
			SyncCommandsUtil.generateMapForSameCommand(
				name, 
				otherNames, 
				new SyncEquipGameUIClientCommand(name, equipment)
			)
		);
	}

	@Override
	public void onUnequipped(EquipmentType type) {
		controller.sendSyncCommandToPlayers(
			SyncCommandsUtil.generateMapForSameCommand(
				name, 
				otherNames, 
				new SyncUnequipGameUIClientCommand(name, type)
			)	
		);
	}

}
