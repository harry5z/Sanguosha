package listeners.game;

import java.awt.event.ActionListener;

import javax.swing.JPanel;

import player.PlayerSimple;
import cards.Card;
import core.PlayerInfo;

public interface GameListener
{
	/**
	 * invoked when a new player is added to game
	 * @param player
	 */
	public void onPlayerAdded(PlayerSimple player);
	/**
	 * invoked when a card on hand is selected
	 * @param card
	 */
	public void setCardSelected(Card card, boolean selected);
	/**
	 * invoked when a target is selected
	 * @param player
	 */
	public void setTargetSelected(PlayerInfo player, boolean selected);
	/**
	 * invoked when a card on hand is set selectable/unselectable
	 * @param card
	 * @param selectable
	 */
	public void setCardSelectable(Card card, boolean selectable);
	/**
	 * invoked when a target is set selectable/unselectable
	 * @param player
	 * @param selectable
	 */
	public void setTargetSelectable(PlayerInfo player, boolean selectable);
	/**
	 * invoked to enable/disable confirm button
	 * @param isEnabled
	 */
	public void setConfirmEnabled(boolean isEnabled);
	/**
	 * invoked to enable/disable cancel button
	 * @param isEnabled
	 */
	public void setCancelEnabled(boolean isEnabled);
	/**
	 * invoked to enable/disable end button
	 * @param isEnabled
	 */
	public void setEndEnabled(boolean isEnabled);
	/**
	 * update the size of game deck
	 * @param size
	 */
	public void setDeckSize(int size);
	
	public void setMessage(String message);
	
	public void clearMessage();
	
	/**
	 * Display the card selection pane on screen
	 * @param player : the owner of these cards
	 * @param showHand : whether to display player's cards on hand
	 * @param showEquipments : whether to display player's equipments
	 * @param showDecisions : whether to display player's decision area
	 */
	public void onDisplayCardSelectionPane(PlayerSimple player, boolean showHand, boolean showEquipments, boolean showDecisions);
	
	public void onDisplayCustomizedSelectionPaneAtCenter(JPanel panel);
	
	public void onRemoveCustomizedSelectionPane();
}