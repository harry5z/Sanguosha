package core.client.game.operations;

import core.client.GamePanel;
import listeners.game.client.PlayerActionListener;
import ui.game.interfaces.Activatable;
/**
 * An operation that listens to user actions (confirm, cancel, select cards/targets, etc.)
 * @author Harry
 *
 */
public interface Operation extends PlayerActionListener {
	
	public void onActivated(GamePanel panel, Activatable source);
	
	default public void onDeactivated() {}
}
