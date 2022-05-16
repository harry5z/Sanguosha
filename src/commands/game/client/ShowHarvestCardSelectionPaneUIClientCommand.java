package commands.game.client;

import java.util.Map;
import java.util.UUID;

import cards.Card;
import core.client.GamePanel;
import core.client.game.operations.instants.HarvestCardSelectionOperation;
import core.player.PlayerInfo;
import ui.game.custom.HarvestSelectionPane;

public class ShowHarvestCardSelectionPaneUIClientCommand extends AbstractGameUIClientCommand {

	private static final long serialVersionUID = 1L;
	
	private final PlayerInfo target;
	private final Map<Card, Boolean> selectableCards;
	private UUID uuid;
	
	public ShowHarvestCardSelectionPaneUIClientCommand(PlayerInfo target, Map<Card, Boolean> selectableCards) {
		this.target = target;
		this.selectableCards = selectableCards;
	}

	@Override
	protected void execute(GamePanel panel) {
		panel.getGameUI().removeSelectionPane();
		
		// clear selection pane command
		if (this.selectableCards == null) {
			return;
		}
		
		// sync selection status command
		if (this.target == null) {
			panel.getGameUI().displayCustomizedSelectionPaneAtCenter(new HarvestSelectionPane(this.selectableCards, null, null));
			return;
		}
		
		// selection command
		if (panel.getGameState().getSelf().getPlayerInfo().equals(this.target)) {
			panel.setNextResponseID(uuid);
			panel.pushOperation(new HarvestCardSelectionOperation(this.target, this.selectableCards));
		} else {
			// only display selection pane if not current target
			panel.getGameUI().displayCustomizedSelectionPaneAtCenter(new HarvestSelectionPane(this.selectableCards, this.target.getName(), null));
		}
	}

	@Override
	public UUID generateResponseID(String name) {
		if (this.target != null && this.target.getName().equals(name)) {
			this.uuid = UUID.randomUUID();
		}
		return uuid;
	}

}
