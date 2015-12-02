package listeners.game.server;

import java.util.Set;

import cards.equipments.Equipment;
import cards.equipments.Equipment.EquipmentType;
import commands.game.client.sync.SyncCommandsUtil;
import commands.game.client.sync.disposal.SyncDisposalAreaRefreshGameUIClientCommand;
import commands.game.client.sync.equipment.SyncEquipGameUIClientCommand;
import commands.game.client.sync.equipment.SyncUnequipGameUIClientCommand;
import core.Deck;
import core.player.PlayerComplete;
import core.server.GameRoom;
import listeners.game.EquipmentListener;

public class ServerInGameEquipmentListener extends ServerInGamePlayerListener implements EquipmentListener {
	
	private final Deck deck;
	private final PlayerComplete player;
	
	public ServerInGameEquipmentListener(PlayerComplete player, Set<String> allNames, GameRoom room) {
		super(player.getName(), allNames, room);
		this.player = player;
		this.deck = room.getGame().getDeck();
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
		deck.discard(player.getEquipment(type));
		room.sendCommandToPlayers(
			SyncCommandsUtil.generateMapForSameCommand(
				name, 
				otherNames, 
				new SyncUnequipGameUIClientCommand(name, type)
			)	
		);
		room.sendCommandToPlayers(
			SyncCommandsUtil.generateMapForSameCommand(
				name, 
				otherNames, 
				new SyncDisposalAreaRefreshGameUIClientCommand()
			)
		);
	}

}
