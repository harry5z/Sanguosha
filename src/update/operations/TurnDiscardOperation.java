package update.operations;

import java.util.ArrayList;

import player.PlayerClientComplete;
import player.PlayerOriginal;
import update.DisposalOfCards;
import update.Update;
import cards.Card;
import core.Framework;
import core.PlayerInfo;

/**
 * Operation that forces a player to discard extra cards on hand if player holds
 * more cards than permitted
 * @author Harry
 *
 */
public class TurnDiscardOperation extends Operation
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -351725130199991268L;
	private PlayerInfo source;
	private ArrayList<Card> cardsDiscarded;//all cards discarded
	private ArrayList<Card> cardsSelected;//cards currently selected
	
	public TurnDiscardOperation(PlayerInfo source,Update next)
	{
		super(next);
		this.source = source;
		cardsDiscarded = new ArrayList<Card>();
		cardsSelected = new ArrayList<Card>();
	}
	@Override
	public void frameworkOperation(Framework framework) 
	{
		framework.sendToAllClients(this);
	}

	@Override
	public void playerOperation(PlayerClientComplete player) 
	{
		System.out.println(player.getName()+" DiscardOperation ");
		if(player.matches(source))
		{
			int numToDiscard = player.getCardsOnHandCount() - player.getCardOnHandLimit();
			if(numToDiscard == 0)//meets requirement
				player.sendToMaster(getNext());//continue the game
			else
			{
				player.setOperation(this);//push to operation
				player.setAllCardsOnHandSelectable(true);//discard any card on hand
				player.getGameListener().setMessage("You need to discard "+numToDiscard+" cards");
			}
		}
	}

	@Override
	public void onPlayerSelected(PlayerClientComplete operator,PlayerOriginal player) 
	{
		//no player selection in this operation
	}

	@Override
	public void onCardSelected(PlayerClientComplete operator, Card card) 
	{
		if(cardsSelected.contains(card))//unselect
		{
			cardsSelected.remove(card);//unselect
			operator.getGameListener().setCardSelected(card, false);//graphically unselect
			if(cardsSelected.size() == 0)//no card selected
			{
				operator.getGameListener().setConfirmEnabled(false);//cannot confirm
			}
		}
		else//select. Note that if more than needed cards are selected, remove the first selected
		{
			int current = operator.getCardsOnHandCount();
			int goal = operator.getCardOnHandLimit();
			if(goal + cardsSelected.size() == current)
				operator.getGameListener().setCardSelected(cardsSelected.remove(0),false);//remove first
			cardsSelected.add(card);
			operator.getGameListener().setCardSelected(card, true);
			operator.getGameListener().setConfirmEnabled(true);
		}
	}

	@Override
	public void onCancelledBy(PlayerClientComplete player) 
	{
		//unable to cancel
	}

	@Override
	public void onConfirmedBy(PlayerClientComplete player) 
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
