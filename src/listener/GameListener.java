package listener;

import player.PlayerOriginalClientSimple;
import core.Card;
import core.Player;
import update.Update;
public interface GameListener 
{
	/**
	 * use it to communicate with master
	 * @param update
	 */
	public void onSendToMaster(Update update);
	
	public void onPlayerAdded(PlayerOriginalClientSimple player);
	
	public void onCardSelected(Card card);
	
	public void onCardUnselected(Card card);
	
	public void onTargetSelected(Player player);
	
	public void onTargetUnselected(Player player);
	
	public void onCardSetSelectable(Card card, boolean selectable);
	
	public void onTargetSetSelectable(Player player, boolean selectable);
	
	public void onConfirmSetEnabled(boolean isEnabled);
	
	public void onCancelSetEnabled(boolean isEnabled);
	
	public void onEndSetEnabled(boolean isEnabled);
}