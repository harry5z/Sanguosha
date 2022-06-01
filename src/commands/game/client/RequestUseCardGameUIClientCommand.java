package commands.game.client;

import java.io.Serializable;
import java.util.Set;
import java.util.function.Predicate;

import cards.Card;
import cards.Card.Suit;
import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.PlayerCardSelectionInGameServerCommand;
import core.client.game.operations.Operation;
import core.client.game.operations.basics.UseCardReactionOperation;
import core.player.PlayerCardZone;
import core.player.PlayerInfo;

public class RequestUseCardGameUIClientCommand extends AbstractSingleTargetOperationGameClientCommand {
	
	public static interface RequestUseCardFilter extends Predicate<Card>, Serializable {
		
		public static RequestUseCardFilter sameSuit(Suit suit) {
			return card -> card.getSuit() == suit;
		}
		
	}

	private static final long serialVersionUID = 1L;
	
	private final String message;
	private final RequestUseCardFilter predicate;
	
	public RequestUseCardGameUIClientCommand(
		PlayerInfo target,
		String message,
		RequestUseCardFilter predicate
	) {
		super(target);
		this.message = message;
		this.predicate = predicate;
	}

	@Override
	protected Operation getOperation() {
		return new UseCardReactionOperation(this.message, this.predicate);
	}

	@Override
	public Set<Class<? extends InGameServerCommand>> getAllowedResponseTypes() {
		return Set.of(PlayerCardSelectionInGameServerCommand.class);
	}

	@Override
	public InGameServerCommand getDefaultResponse() {
		return new PlayerCardSelectionInGameServerCommand(null, PlayerCardZone.HAND);
	}
	
	@Override
	protected String getMessageForOthers() {
		return "Waiting on " + target.getName() + " to use a card";
	}

}
