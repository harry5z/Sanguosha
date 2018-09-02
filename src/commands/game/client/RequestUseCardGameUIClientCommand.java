package commands.game.client;

import java.util.Collection;

import core.client.game.operations.Operation;
import core.client.game.operations.basics.UseCardReactionOperation;
import core.event.game.basic.RequestUseCardEvent.RequestUseCardPredicate;
import core.player.PlayerInfo;

public class RequestUseCardGameUIClientCommand extends AbstractSingleTargetOperationGameClientCommand {

	private static final long serialVersionUID = 1L;
	
	private final String message;
	private final Collection<RequestUseCardPredicate> predicates;
	
	public RequestUseCardGameUIClientCommand(
		PlayerInfo target,
		String message,
		Collection<RequestUseCardPredicate> predicates
	) {
		super(target);
		this.message = message;
		this.predicates = predicates;
	}


	@Override
	protected Operation getOperation() {
		return new UseCardReactionOperation(this.message, this.predicates);
	}

}
