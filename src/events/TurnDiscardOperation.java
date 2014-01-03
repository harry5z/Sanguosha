package events;

import java.util.ArrayList;
import player.PlayerOriginal;
import player.PlayerOriginalClientComplete;
import update.DisposalOfCards;
import core.Card;
import core.Framework;
import core.Operation;
import core.PlayerInfo;
import core.Update;


public class TurnDiscardOperation implements Operation
{
	private PlayerInfo source;
	private ArrayList<Card> cardsDiscarded;
	private ArrayList<Card> cardsSelected;
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
		if(player.isEqualTo(source))
		{
			if(player.getCardsOnHandCount() == player.getCardOnHandLimit())
				player.sendToMaster(next);
			else
			{
				player.setOperation(this);
				player.setAllCardsOnHandSelectable(true);
			}
		}
	}

	@Override
	public void onPlayerSelected(PlayerOriginalClientComplete operator,PlayerOriginal player) {
		// TODO Auto-generated method stub
		
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
				operator.setCardOnHandSelected(cardsSelected.remove(0),false);
			cardsSelected.add(card);
			operator.setCardOnHandSelected(card, true);
			operator.setConfirmEnabled(true);
		}
	}

	@Override
	public void onCancelledBy(PlayerOriginalClientComplete player) {
		// TODO Auto-generated method stub
		
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
