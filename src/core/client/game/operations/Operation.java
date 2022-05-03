package core.client.game.operations;

import core.client.GamePanel;
import ui.game.interfaces.CardUI;
import ui.game.interfaces.EquipmentUI;
import ui.game.interfaces.PlayerUI;
/**
 * An operation that listens to user actions (confirm, cancel, select cards/targets, etc.)
 * 
 * When an Operation is pushed, {@link #onActivated()} is called once. An Operation is
 * responsible for updating the UI when {@link #onLoaded()} is called, and resetting all
 * changes when {@link #onUnloaded()} is called
 * 
 * @author Harry
 *
 */
public interface Operation {
	
	default public void onCardClicked(CardUI card) {}
	
	default public void onPlayerClicked(PlayerUI player) {}
	
	/**
	 * Called when an equipment in the card selection pane is clicked
	 * 
	 * @param equipment
	 */
	default public void onEquipmentClicked(EquipmentUI equipment) {}
	
	/**
	 * Called when a Delayed Special card in the card selection pane is clicked
	 * 
	 * @param equipment
	 */
	default public void onDelayedClicked(CardUI card) {}
	
	/**
	 * <p>Called when the CONFIRM button is pressed</p>
	 * 
	 * <p>By default, it calls {@link #onUnloaded()} and {@link #onDeactivated()}</p>
	 * 
	 * Do not override this method unless this Operation enables the CONFIRM button, 
	 */
	public void onConfirmed();
	
	/**
	 * <p>Called when the CANCEL button is pressed</p>
	 * 
	 * By default, {@link #onCanceled()} would call {@link #onUnloaded()}
	 * to reset all UI changes, then pop the current operation and call 
	 * {@link #onLoaded()} on the previous operation, if any
	 */
	public void onCanceled();
	
	/**
	 * <p>Called when the END button is pressed. This should only happen in a player's
	 * DEAL stage</p>
	 * 
	 * By default, {@link #onEnded()} would call {@link #onUnloaded()}
	 * to reset all UI changes, then pop the current operation and call 
	 * {@link #onEnded()} on the previous operation, if any
	 */
	public void onEnded();
	
	/**
	 * Performs UI update to enable all actionable buttons, e.g. cards, equipments.
	 * Also sends necessary ClientGameEvent for skill activation.
	 */
	public void onLoaded();
		
	/**
	 * Performs UI reset. Reverts all UI changes made in {@link #onLoaded()}
	 */
	public void onUnloaded();

	/**
	 * <p>Called once when this Operation is pushed</p>
	 * 
	 * By default, it simply stores the reference to the GamePanel, then calls {@link #onLoaded()}
	 * 
	 * @param panel
	 */
	public void onActivated(GamePanel panel);
	
	/**
	 * <p>Called when a top level Operation needs to clean up
	 * all other Operation</p>
	 * 
	 * By default, {@link #onDeactivated()} would simply pop the current operation and call 
	 * {@link #onDeactivated()} on the previous operation, if any
	 */
	public void onDeactivated();
}
