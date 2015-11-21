package listeners.game.server;

import java.util.Set;
import java.util.stream.Collectors;

import cards.equipments.Equipment;
import cards.equipments.Equipment.EquipmentType;
import core.Deck;
import exceptions.server.game.InvalidPlayerCommandException;
import listeners.game.EquipmentListener;
import net.server.GameRoom;
import player.PlayerComplete;
import ui.game.GamePanelUI;

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
		Equipment existing = player.getEquipment(equipment.getEquipmentType());
		if (existing != null) {
			deck.discard(existing);
		}
		room.sendCommandToPlayer(
			name, 
			(ui, connection) -> {
				try {
					ui.<GamePanelUI>getPanel().getContent().getSelf().equip(equipment);
				} catch (InvalidPlayerCommandException e) {
					e.printStackTrace();
				}
			}
		);
		final String playerName = name; // To avoid referencing "this" while serializing
		room.sendCommandToPlayers(
			otherNames.stream().collect(
				Collectors.toMap(
					n -> n, 
					n -> ((ui, connection) -> {
						try {
							ui.<GamePanelUI>getPanel().getContent().getPlayer(playerName).equip(equipment);
						} catch (InvalidPlayerCommandException e) {
							e.printStackTrace();
						}
					})
				)
			)
		);
	}

	@Override
	public void onUnequipped(EquipmentType type) {
		deck.discard(player.getEquipment(type));
		room.sendCommandToPlayer(
			name,
			(ui, connection) -> {
				try {
					ui.<GamePanelUI>getPanel().getContent().getSelf().unequip(type);
				} catch (InvalidPlayerCommandException e) {
					e.printStackTrace();
				}
			}
		);
		final String playerName = name; // To avoid referencing "this" while serializing
		room.sendCommandToPlayers(
			otherNames.stream().collect(
				Collectors.toMap(
					n -> n, 
					n -> ((ui, connection) -> {
						try {
							ui.<GamePanelUI>getPanel().getContent().getPlayer(playerName).unequip(type);
						} catch (InvalidPlayerCommandException e) {
							e.printStackTrace();
						}
					})
				)
			)
		);
	}

}
