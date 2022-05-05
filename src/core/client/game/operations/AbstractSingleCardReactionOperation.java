package core.client.game.operations;

import cards.equipments.Equipment.EquipmentType;
import commands.game.server.ingame.InGameServerCommand;
import core.player.PlayerSimple;

/**
 * A generic Operation for when the player is expected to use a card to react
 * to some event (e.g. use Dodge/Neutralization)
 * 
 * @author Harry
 *
 */
public abstract class AbstractSingleCardReactionOperation extends AbstractMultiCardMultiTargetOperation {

	private final String message;
	
	public AbstractSingleCardReactionOperation(String message) {
		super(1, 0);
		this.message = message;
	}

	@Override
	public final void onCanceled() {
		if (this.cards.size() == 1) { // Unselect the current card
			this.onCardClicked(this.getFirstCardUI());
		} else { // Give up card reaction
			this.onUnloaded();
			this.onDeactivated();
			this.panel.getChannel().send(getCommandOnCancel());
		}
	}
	
	@Override
	protected final String getMessage() {
		return this.message;
	}
	
	@Override
	protected final boolean isConfirmEnabled() {
		return this.cards.size() == 1;
	}
	
	@Override
	protected final boolean isEquipmentTypeActivatable(EquipmentType type) {
		return false;
	}
	
	@Override
	protected final boolean isPlayerActivatable(PlayerSimple player) {
		return false;
	}
	
	protected abstract InGameServerCommand getCommandOnCancel();
}
