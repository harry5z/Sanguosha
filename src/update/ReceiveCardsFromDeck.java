package update;

import java.util.ArrayList;

import player.PlayerOriginalClientComplete;
import core.Card;
import core.Framework;
import core.Player;
import core.Update;

public class ReceiveCardsFromDeck implements Update
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3227087424276735239L;
	private ArrayList<Card> cards;
	private Player target;
	
	public ReceiveCardsFromDeck(Player target, ArrayList<Card> cards)
	{
		this.target = target;
		this.cards = cards;
	}

	@Override
	public void frameworkOperation(Framework framework) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void playerOperation(PlayerOriginalClientComplete player) {
		if(player.equals(target))
			player.addCards(cards);
		else
			player.findMatch(target).addCards(cards);
		
	}

}
