package commands.client.game.sync.ui;

import java.util.Map;

import cards.Card;
import ui.game.custom.HarvestSelectionPane;
import ui.game.interfaces.GameUI;

public class SyncHarvestCardSelectionPaneGameUIClientCommand extends AbstractCustomGameUIClientCommand {

	private static final long serialVersionUID = 1L;
	
	private final Map<Card, Boolean> selectableCards;
	
	public SyncHarvestCardSelectionPaneGameUIClientCommand(Map<Card, Boolean> selectableCards) {
		this.selectableCards = selectableCards;
	}

	@Override
	protected void sync(GameUI ui) {
		ui.removeSelectionPane();
		
		// clear selection pane command
		if (this.selectableCards == null) {
			return;
		}
		
		// sync selection status command
		ui.displayCustomizedSelectionPaneAtCenter(new HarvestSelectionPane(this.selectableCards, null, null));
	}

}
