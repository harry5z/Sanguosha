package core.client.game.operations.mechanics;

import java.util.Arrays;
import java.util.Collection;

import cards.equipments.Equipment.EquipmentType;
import commands.game.server.ingame.PlayerCardSelectionInGameServerCommand;
import core.client.game.operations.AbstractOperation;
import core.player.PlayerCardZone;
import core.player.PlayerInfo;
import core.player.PlayerSimple;
import ui.game.custom.CardSelectionPane;
import ui.game.interfaces.CardUI;

public class PlayerCardSelectionOperation extends AbstractOperation {
	
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
	public void onSelectionPaneCardClicked(CardUI card, PlayerCardZone zone) {
		this.onUnloaded();
		this.onDeactivated();
		this.panel.getChannel().send(new PlayerCardSelectionInGameServerCommand(card.getCard(), zone));
	}

	@Override
	public void onLoaded() {
		PlayerSimple target = panel.getGameState().getPlayer(this.target.getName());
		panel.getGameUI().displayCustomizedSelectionPaneAtCenter(new CardSelectionPane(target, this.zones, this.equipmentTypes, panel));
	}
	
	@Override
	public void onUnloaded() {
		this.panel.getGameUI().removeSelectionPane();
	}
}
