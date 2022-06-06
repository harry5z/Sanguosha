package core.client.game.operations.instants;

import java.util.Map;

import cards.Card;
import commands.server.ingame.PlayerCardSelectionInGameServerCommand;
import core.client.game.operations.AbstractOperation;
import core.player.PlayerCardZone;
import core.player.PlayerInfo;
import ui.game.custom.HarvestSelectionPane;
import ui.game.interfaces.CardUI;

public class HarvestCardSelectionOperation extends AbstractOperation {
	
	private final PlayerInfo target;
	private final Map<Card, Boolean> selectableCards;
	
	public HarvestCardSelectionOperation(PlayerInfo target, Map<Card, Boolean> selectableCards) {
		this.target = target;
		this.selectableCards = selectableCards;
	}
	
	@Override
	public void onSelectionPaneCardClicked(CardUI card, PlayerCardZone zone) {
		this.onUnloaded();
		this.onDeactivated();
		this.panel.sendResponse(new PlayerCardSelectionInGameServerCommand(card.getCard(), null));
	}

	@Override
	public void onLoaded() {
		this.panel.getGameUI().displayCustomizedSelectionPaneAtCenter(new HarvestSelectionPane(this.selectableCards, this.target.getName(), panel));
	}
	
	@Override
	public void onUnloaded() {
		this.panel.getGameUI().removeSelectionPane();
	}

}
