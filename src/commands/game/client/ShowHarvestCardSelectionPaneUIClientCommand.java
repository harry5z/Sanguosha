package commands.game.client;

import java.util.Map;

import cards.Card;
import core.client.GamePanel;
import core.client.game.operations.HarvestCardSelectionOperation;
import core.player.PlayerInfo;
import ui.game.custom.HarvestSelectionPane;

public class ShowHarvestCardSelectionPaneUIClientCommand extends AbstractGameUIClientCommand {

	private static final long serialVersionUID = 1L;
	
	private final PlayerInfo target;
	private final Map<Card, Boolean> selectableCards;
	
	public ShowHarvestCardSelectionPaneUIClientCommand(PlayerInfo target, Map<Card, Boolean> selectableCards) {
		this.target = target;
		this.selectableCards = selectableCards;
	}

	@Override
	protected void execute(GamePanel panel) {
		panel.getContent().removeSelectionPane();
		
		// clear selection pane command
		if (this.selectableCards == null) {
			return;
		}
		
		// sync selection status command
		if (this.target == null) {
			panel.getContent().displayCustomizedSelectionPaneAtCenter(new HarvestSelectionPane(this.selectableCards, null, null));
			return;
		}
		
		// selection command
		if (panel.getContent().getSelf().getPlayerInfo().equals(this.target)) {
			panel.pushOperation(new HarvestCardSelectionOperation(this.target, this.selectableCards));
		} else {
			// only display selection pane if not current target
			panel.getContent().displayCustomizedSelectionPaneAtCenter(new HarvestSelectionPane(this.selectableCards, this.target.getName(), null));
		}
	}

}
