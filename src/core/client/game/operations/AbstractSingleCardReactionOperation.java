package core.client.game.operations;

/**
 * A generic Operation for when the player is expected to use a card to react
 * to some event (e.g. use Dodge/Neutralization)
 * 
 * @author Harry
 *
 */
public abstract class AbstractSingleCardReactionOperation extends AbstractMultiCardNoTargetReactionOperation {

	private final String message;
	
	public AbstractSingleCardReactionOperation(String message) {
		super(1);
		this.message = message;
	}

	@Override
	protected final String getMessage() {
		return this.message;
	}
	
}
