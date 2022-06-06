package core.client.game.operations;

import java.util.HashSet;

import cards.equipments.Equipment.EquipmentType;
import commands.server.ingame.InGameServerCommand;
import core.player.PlayerSimple;
import ui.game.interfaces.CardUI;

/**
 * A generic card usage only reaction Operation. Such an Operation has a separate command
 * to send when the player gives up reaction (by pressing CANCEL), and may or may not allow
 * the CANCEL button to be pressed (i.e. inaction may not be an option, e.g. Show Card)
 * 
 * @author Harry
 *
 */
public abstract class AbstractMultiCardNoTargetReactionOperation extends AbstractMultiCardMultiTargetOperation {

	public AbstractMultiCardNoTargetReactionOperation(int maxCards) {
		super(maxCards, 0);
	}

	@Override
	public final void onCanceled() {
		if (this.cards.size() > 0) { // Unselect all cards
			for (CardUI card : new HashSet<>(this.cards.keySet())) {
				this.onCardClicked(card);
			}
		} else { // Give up card reaction
			this.onUnloaded();
			this.onDeactivated();
			this.panel.sendResponse(getCommandOnInaction());
		}
	}
	
	@Override
	protected boolean isConfirmEnabled() {
		return this.cards.size() == this.maxCards;
	}
	
	@Override
	protected boolean isEquipmentTypeActivatable(EquipmentType type) {
		return false;
	}
	
	@Override
	protected final boolean isPlayerActivatable(PlayerSimple player) {
		return false;
	}
	
	/**
	 * <p>The command to be sent when player gives up on reaction. If giving up
	 * is not allowed (i.e. {@link #isCancelEnabled()} does not always return true),
	 * this method may return null</p>
	 * 
	 * @return command upon inaction, or null if inaction is not allowed
	 */
	protected abstract InGameServerCommand getCommandOnInaction();

}
