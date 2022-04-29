package core.client.game.operations.instants;

import cards.Card;
import cards.specials.instant.Neutralization;
import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.NeutralizationReactionInGameServerCommand;
import core.client.GamePanel;
import core.client.game.operations.AbstractCardReactionOperation;
import ui.game.interfaces.Activatable;
import ui.game.interfaces.CardUI;

public class NeutralizationOperation extends AbstractCardReactionOperation {

	public NeutralizationOperation(String message) {
		super(message);
	}

	@Override
	public void onEnded() {
       if (this.card != null) {
           this.card.setActivated(false);
           this.card = null;
       }
       panel.getGameUI().setConfirmEnabled(false);
       panel.getGameUI().setCancelEnabled(false);
       for (CardUI ui : this.panel.getGameUI().getCardRackUI().getCardUIs()) {
           ui.setActivatable(false);
       }
	}

	@Override
	public void onActivated(GamePanel panel, Activatable source) {
		if (panel.getCurrentOperation() instanceof NeutralizationOperation) {
			panel.getCurrentOperation().onEnded();
			panel.popOperation();
		}
		super.onActivated(panel, source);
	}

	@Override
	protected boolean isCardActivatable(Card card) {
		return card instanceof Neutralization;
	}
	
	@Override
	protected boolean isCancelEnabled() {
		return true;
	}

	@Override
	protected InGameServerCommand getCommand(Card card) {
		if (card == null) {
			return new NeutralizationReactionInGameServerCommand(null, null);
		} else {
			return new NeutralizationReactionInGameServerCommand(this.panel.getGameState().getSelf().getPlayerInfo(), card);
		}
	}
}
