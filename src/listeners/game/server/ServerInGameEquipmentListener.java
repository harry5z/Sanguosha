package listeners.game.server;

import java.util.Set;

import cards.equipments.Equipment;
import cards.equipments.Equipment.EquipmentType;
import commands.client.game.sync.SyncCommandsUtil;
import commands.client.game.sync.player.SyncEquipGameClientCommand;
import commands.client.game.sync.player.SyncUnequipGameClientCommand;
import core.player.PlayerCompleteServer;
import core.player.PlayerSimple;
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
				new SyncEquipGameClientCommand(name, equipment)
			)
		);
	}

	@Override
	public void onUnequipped(EquipmentType type) {
		controller.sendSyncCommandToPlayers(
			SyncCommandsUtil.generateMapForSameCommand(
				name, 
				otherNames, 
				new SyncUnequipGameClientCommand(name, type)
			)	
		);
	}

	@Override
	public void refreshSelf(PlayerCompleteServer self) {
		for (Equipment equipment : self.getEquipments()) {
			controller.sendSyncCommandToPlayer(name, new SyncEquipGameClientCommand(self.getName(), equipment));
		}
	}

	@Override
	public void refreshOther(PlayerSimple other) {
		for (Equipment equipment : other.getEquipments()) {
			controller.sendSyncCommandToPlayer(name, new SyncEquipGameClientCommand(other.getName(), equipment));
		}
	}

}
