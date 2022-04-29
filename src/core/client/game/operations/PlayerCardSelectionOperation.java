package core.client.game.operations;

import java.util.Arrays;
import java.util.Collection;

import cards.equipments.Equipment.EquipmentType;
import commands.game.server.ingame.PlayerCardSelectionInGameServerCommand;
import core.client.GamePanel;
import core.player.PlayerCardZone;
import core.player.PlayerInfo;
import core.player.PlayerSimple;
import ui.game.custom.CardSelectionPane;
import ui.game.interfaces.Activatable;
import ui.game.interfaces.CardUI;
import ui.game.interfaces.EquipmentUI;

public class PlayerCardSelectionOperation implements Operation {
	
	private GamePanel panel;
	private final PlayerInfo target;
	private final Collection<PlayerCardZone> zones;
	private final Collection<EquipmentType> equipmentTypes;
	
	public PlayerCardSelectionOperation(
		PlayerInfo target,
		Collection<PlayerCardZone> zones,
		Collection<EquipmentType> equipmentTypes
	) {
		this.target = target;
		this.zones = zones;
		this.equipmentTypes = equipmentTypes != null ? equipmentTypes : Arrays.asList(EquipmentType.values());
	}

	@Override
	public void onCardClicked(CardUI card) {
		this.panel.getGameUI().removeSelectionPane();
		this.panel.popOperation();
		this.panel.getChannel().send(new PlayerCardSelectionInGameServerCommand(card.getCard(), PlayerCardZone.HAND));
	}
	
	@Override
	public void onEquipmentClicked(EquipmentUI equipment) {
		this.panel.getGameUI().removeSelectionPane();
		this.panel.popOperation();
		this.panel.getChannel().send(new PlayerCardSelectionInGameServerCommand(equipment.getEquipment(), PlayerCardZone.EQUIPMENT));
	}
	
	@Override
	public void onDelayedClicked(CardUI card) {
		this.panel.getGameUI().removeSelectionPane();
		this.panel.popOperation();
		this.panel.getChannel().send(new PlayerCardSelectionInGameServerCommand(card.getCard(), PlayerCardZone.DELAYED));
	}

	@Override
	public void onActivated(GamePanel panel, Activatable source) {
		this.panel = panel;
		PlayerSimple target = panel.getGameState().getPlayer(this.target.getName());
		panel.getGameUI().displayCustomizedSelectionPaneAtCenter(new CardSelectionPane(target, this.zones, this.equipmentTypes, panel));
	}
}
