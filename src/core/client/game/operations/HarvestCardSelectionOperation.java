package core.client.game.operations;

import java.util.Map;

import cards.Card;
import commands.game.server.ingame.PlayerCardSelectionInGameServerCommand;
import core.client.GamePanel;
import core.player.PlayerInfo;
import ui.game.custom.HarvestSelectionPane;
import ui.game.interfaces.Activatable;
import ui.game.interfaces.CardUI;

public class HarvestCardSelectionOperation implements Operation {
	
	private GamePanel panel;
	private final PlayerInfo target;
	private final Map<Card, Boolean> selectableCards;
	
	public HarvestCardSelectionOperation(PlayerInfo target, Map<Card, Boolean> selectableCards) {
		this.target = target;
		this.selectableCards = selectableCards;
	}
	
	@Override
	public void onCardClicked(CardUI card) {
		this.panel.getGameUI().removeSelectionPane();
		this.panel.popOperation();
		this.panel.getChannel().send(new PlayerCardSelectionInGameServerCommand(card.getCard(), null));
	}

	@Override
	public void onActivated(GamePanel panel, Activatable source) {
		this.panel = panel;
		panel.getGameUI().displayCustomizedSelectionPaneAtCenter(new HarvestSelectionPane(this.selectableCards, this.target.getName(), panel));
	}

}
