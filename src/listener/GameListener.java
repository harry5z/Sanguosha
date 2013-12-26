package listener;

import core.Card;
import core.Player;
import update.Update;
public interface GameListener 
{
	/**
	 * Send game update to GUI
	 * @param note
	 */
	public void onNotified(Update update);
	/**
	 * use it to communicate with master
	 * @param update
	 */
	public void onSendToMaster(Update update);
	
	public void onPlayerAdded(Player player);
	
	public void onTurnDealStarted();
	
	public void onTurnDealEnded();
	
	public void onCardSelected(Card card);
	
	public void onCardUnselected(Card card);
	
	public void onTargetSelected(Player player);
	
	public void onTargetUnselected(Player player);
	
	public void onCardSetSelectable(Card card, boolean selectable);
	
}