package listeners.game;

import cards.Card;
import core.player.PlayerInfo;
import core.player.PlayerSimple;

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
	 * update the size of game deck
	 * @param size
	 */
	public void setDeckSize(int size);
	
}