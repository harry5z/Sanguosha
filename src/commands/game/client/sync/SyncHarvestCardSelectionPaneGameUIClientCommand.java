package commands.game.client.sync;

import java.util.Map;

import cards.Card;
import core.client.GamePanel;
import ui.game.custom.HarvestSelectionPane;

public class SyncHarvestCardSelectionPaneGameUIClientCommand extends AbstractSyncGameUIClientCommand {

	private static final long serialVersionUID = 1L;
	
	private final Map<Card, Boolean> selectableCards;
	
	public SyncHarvestCardSelectionPaneGameUIClientCommand(Map<Card, Boolean> selectableCards) {
		this.selectableCards = selectableCards;
	}

	@Override
	protected void sync(GamePanel panel) {
		panel.getGameUI().removeSelectionPane();
		
		// clear selection pane command
		if (this.selectableCards == null) {
			return;
		}
		
		// sync selection status command
		panel.getGameUI().displayCustomizedSelectionPaneAtCenter(new HarvestSelectionPane(this.selectableCards, null, null));
	}

}
