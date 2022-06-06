package core.client.game.operations.instants;

import cards.Card;
import cards.specials.instant.Nullification;
import commands.server.ingame.InGameServerCommand;
import commands.server.ingame.NullificationReactionInGameServerCommand;
import core.client.game.event.RequestNullificationClientGameEvent;
import core.client.game.operations.AbstractSingleCardReactionOperation;

/**
 * <p>When a player uses an Instant Special card, all players have the option to
 * use Nullification to cancel the effect. This applies to Nullification itself
 * too, i.e. one can use Nullification to "cancel" another Nullification.</p>
 * 
 * As such, a player's Operation stack may have multiple NullificationOperation,
 * 
 * @author Harry
 *
 */
public class NullificationOperation extends AbstractSingleCardReactionOperation {

	public NullificationOperation(String message) {
		super(message);
	}

	@Override
	protected boolean isCardActivatable(Card card) {
		return card instanceof Nullification;
	}
	
	@Override
	protected boolean isCancelEnabled() {
		return true;
	}

	@Override
	protected void onLoadedCustom() {
		this.panel.emit(new RequestNullificationClientGameEvent(true));
	}

	@Override
	protected void onUnloadedCustom() {
		this.panel.emit(new RequestNullificationClientGameEvent(false));
	}

	@Override
	protected InGameServerCommand getCommandOnInaction() {
		return new NullificationReactionInGameServerCommand(null);
	}

	@Override
	protected InGameServerCommand getCommandOnConfirm() {
		return new NullificationReactionInGameServerCommand(this.getFirstCardUI().getCard());
	}
}
