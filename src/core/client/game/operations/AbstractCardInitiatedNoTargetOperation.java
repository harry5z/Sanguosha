package core.client.game.operations;

import core.player.PlayerSimple;
import ui.game.interfaces.Activatable;

/**
 * A generic target-less card usage action during a player's DEAL stage, e.g. Peach
 * 
 * @author Harry
 *
 */
public abstract class AbstractCardInitiatedNoTargetOperation extends AbstractCardInitiatedMultiTargetOperation {

	public AbstractCardInitiatedNoTargetOperation(Activatable card) {
		super(card, 0);
	}

	@Override
	protected boolean isPlayerActivatable(PlayerSimple player) {
		return false;
	}

}
