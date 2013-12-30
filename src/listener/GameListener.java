package listener;

import player.PlayerOriginalClientSimple;
import core.Card;
import core.Player;
import core.PlayerInfo;
import core.Update;
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
	
	public void onTargetSelected(PlayerInfo player);
	
	public void onTargetUnselected(PlayerInfo player);
	
	public void onCardSetSelectable(Card card, boolean selectable);
	
	public void onTargetSetSelectable(PlayerInfo player, boolean selectable);
	
	public void onConfirmSetEnabled(boolean isEnabled);
	
	public void onCancelSetEnabled(boolean isEnabled);
	
	public void onEndSetEnabled(boolean isEnabled);
}