package core.client.game.operations.basics;

import cards.Card;
import cards.basics.Peach;
import cards.basics.Wine;
import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.RescueReactionInGameServerCommand;
import core.client.game.operations.AbstractSingleCardReactionOperation;
import core.player.PlayerInfo;

public class RescueReactionOperation extends AbstractSingleCardReactionOperation {

	private final PlayerInfo dyingPlayer;
	
	public RescueReactionOperation(PlayerInfo dyingPlayer, String message) {
		super(message);
		this.dyingPlayer = dyingPlayer;
	}

	@Override
	protected boolean isCancelEnabled() {
		return true;
	}

	@Override
	protected boolean isCardActivatable(Card card) {
		if (this.getSelf().getPlayerInfo().equals(dyingPlayer)) {
			// may use Wine or Peach to save oneself
			return card instanceof Wine || card instanceof Peach;
		} else {
			// may use Peach to save others
			return card instanceof Peach;
		}
	}

	@Override
	protected void onLoadedCustom() {
		// TODO send event to activate skills
	}

	@Override
	protected void onUnloadedCustom() {
		// TODO send event to deactivate skills
	}

	@Override
	protected InGameServerCommand getCommandOnConfirm() {
		return new RescueReactionInGameServerCommand(this.getFirstCardUI().getCard());
	}

	@Override
	protected InGameServerCommand getCommandOnInaction() {
		return new RescueReactionInGameServerCommand(null);
	}
}
