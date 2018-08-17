package core.client.game.operations;

import java.util.Collection;

import commands.game.server.ingame.PlayerCardSelectionInGameServerCommand;
import core.client.GamePanel;
import core.heroes.Hero;
import core.player.PlayerCardZone;
import core.player.PlayerInfo;
import core.player.PlayerSimple;
import ui.game.custom.CardSelectionPane;
import ui.game.interfaces.Activatable;
import ui.game.interfaces.CardUI;
import ui.game.interfaces.EquipmentUI;

public class PlayerCardSelectionOperation implements Operation {
	
	private GamePanel<? extends Hero> panel;
	private final PlayerInfo target;
	private final Collection<PlayerCardZone> zones;
	
	public PlayerCardSelectionOperation(PlayerInfo target, Collection<PlayerCardZone> zones) {
		this.target = target;
		this.zones = zones;
	}

	@Override
	public void onCardClicked(CardUI card) {
		this.panel.getContent().removeSelectionPane();
		this.panel.popOperation();
		this.panel.getChannel().send(new PlayerCardSelectionInGameServerCommand(card.getCard(), PlayerCardZone.HAND));
	}
	
	@Override
	public void onEquipmentClicked(EquipmentUI equipment) {
		this.panel.getContent().removeSelectionPane();
		this.panel.popOperation();
		this.panel.getChannel().send(new PlayerCardSelectionInGameServerCommand(equipment.getEquipment(), PlayerCardZone.EQUIPMENT));
	}

	@Override
	public void onActivated(GamePanel<? extends Hero> panel, Activatable source) {
		this.panel = panel;
		PlayerSimple target = panel.getContent().getPlayer(this.target.getName());
		panel.getContent().displayCustomizedSelectionPaneAtCenter(new CardSelectionPane(target, this.zones, panel));
	}
}
