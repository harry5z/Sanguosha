package update.operations;

import java.util.ArrayList;

import player.PlayerOriginal;
import player.PlayerOriginalClientComplete;
import update.DisposalOfCards;
import update.Update;
import core.Card;
import core.Framework;
import core.Operation;
import core.PlayerInfo;

/**
 * Operation that forces a player to discard extra cards on hand if player holds
 * more cards than permitted
 * @author Harry
 *
 */
public class TurnDiscardOperation implements Operation
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -351725130199991268L;
	private PlayerInfo source;
	private ArrayList<Card> cardsDiscarded;//all cards discarded
	private ArrayList<Card> cardsSelected;//cards currently selected
	private Update next;
	
	public TurnDiscardOperation(PlayerInfo source,Update next)
	{
		this.source = source;
		this.next = next;
		cardsDiscarded = new ArrayList<Card>();
		cardsSelected = new ArrayList<Card>();
	}
	@Override
	public void frameworkOperation(Framework framework) 
	{
		framework.sendToAllClients(this);
	}

	@Override
	public void playerOperation(PlayerOriginalClientComplete player) 
	{
		System.out.println(player.getName()+" DiscardOperation ");
		if(player.matches(source))
		{
			if(player.getCardsOnHandCount() == player.getCardOnHandLimit())//meets requirement
				player.sendToMaster(next);//continue the game
			else
			{
				player.setOperation(this);//push to operation
				player.setAllCardsOnHandSelectable(true);//discard any card on hand
			}
		}
	}

	@Override
	public void onPlayerSelected(PlayerOriginalClientComplete operator,PlayerOriginal player) 
	{
		//no player selection in this operation
	}

	@Override
	public void onCardSelected(PlayerOriginalClientComplete operator, Card card) 
	{
		if(cardsSelected.contains(card))//unselect
		{
			cardsSelected.remove(card);//unselect
			operator.setCardOnHandSelected(card, false);//graphically unselect
			if(cardsSelected.size() == 0)//no card selected
			{
				operator.setConfirmEnabled(false);//cannot confirm
			}
		}
		else//select. Note that if more than needed cards are selected, remove the first selected
		{
			int current = operator.getCardsOnHandCount();
			int goal = operator.getCardOnHandLimit();
			if(goal + cardsSelected.size() == current)
				operator.setCardOnHandSelected(cardsSelected.remove(0),false);//remove first
			cardsSelected.add(card);
			operator.setCardOnHandSelected(card, true);
			operator.setConfirmEnabled(true);
		}
	}

	@Override
	public void onCancelledBy(PlayerOriginalClientComplete player) 
	{
		//unable to cancel
	}

	@Override
	public void onConfirmedBy(PlayerOriginalClientComplete player) 
	{
		cardsDiscarded.addAll(cardsSelected);
		ArrayList<Card> temp = new ArrayList<Card>();
		for(Card card : cardsSelected)
			temp.add(card);
		cardsSelected.clear();
		player.setOperation(null);
		player.sendToMaster(new DisposalOfCards(source,temp,this));
	}

}
