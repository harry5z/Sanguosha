package core.client.game.operations.instants;

import cards.Card;
import cards.specials.instant.Neutralization;
import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.NeutralizationReactionInGameServerCommand;
import core.client.game.event.RequestNeutralizationClientGameEvent;
import core.client.game.operations.AbstractCardReactionOperation;

/**
 * <p>When a player uses an Instant Special card, all players have the option to
 * use Neutralization to cancel the effect. This applies to Neutralization itself
 * too, i.e. one can use Neutralization to "cancel" another Neutralization.</p>
 * 
 * As such, a player's Operation stack may have multiple NeutralizationOperation,
 * 
 * @author Harry
 *
 */
public class NeutralizationOperation extends AbstractCardReactionOperation {

	public NeutralizationOperation(String message) {
		super(message);
	}

	@Override
	protected boolean isCardActivatable(Card card) {
		return card instanceof Neutralization;
	}
	
	@Override
	protected boolean isCancelAllowed() {
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

	@Override
	protected void onLoadedCustom() {
		this.panel.emit(new RequestNeutralizationClientGameEvent(true));
	}

	@Override
	protected void onUnloadedCustom() {
		this.panel.emit(new RequestNeutralizationClientGameEvent(false));
	}
}
