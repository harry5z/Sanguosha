package commands.game.client;

import java.util.Map;
import java.util.Set;

import cards.Card;
import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.PlayerCardSelectionInGameServerCommand;
import core.client.GamePanel;
import core.client.game.operations.Operation;
import core.client.game.operations.instants.HarvestCardSelectionOperation;
import core.player.PlayerInfo;
import ui.game.custom.HarvestSelectionPane;

public class ShowHarvestCardSelectionPaneUIClientCommand extends AbstractSingleTargetOperationGameClientCommand {

	private static final long serialVersionUID = 1L;
	
	private final Map<Card, Boolean> selectableCards;
	
	public ShowHarvestCardSelectionPaneUIClientCommand(PlayerInfo target, Map<Card, Boolean> selectableCards) {
		super(target);
		this.selectableCards = selectableCards;
	}

	@Override
	protected Operation getOperation() {
		return new HarvestCardSelectionOperation(this.target, this.selectableCards);
	}
	
	@Override
	public Set<Class<? extends InGameServerCommand>> getAllowedResponseTypes() {
		return Set.of(PlayerCardSelectionInGameServerCommand.class);
	}
	
	@Override
	protected boolean shouldClearGamePanel() {
		return true;
	}
	
	@Override
	protected void updateForOtherPlayer(GamePanel panel) {
		panel.getGameUI().displayCustomizedSelectionPaneAtCenter(new HarvestSelectionPane(this.selectableCards, this.target.getName(), null));
	}

	@Override
	public InGameServerCommand getDefaultResponse() {
		for (Map.Entry<Card, Boolean> entry : selectableCards.entrySet()) {
			if (!entry.getValue()) {
				return new PlayerCardSelectionInGameServerCommand(entry.getKey(), null);
			}
		}
		// by design we should not reach here
		throw new RuntimeException("Harvest has no valid card to select");
	}

}
