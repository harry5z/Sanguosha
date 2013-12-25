package update;

import java.util.ArrayList;

import core.Card;
import core.Player;

public class ReceiveCardsFromDeck extends Update
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
	public void playerOperation(Player player)
	{
		if(player.equals(target))
			player.addCards(cards);
		else
			player.findMatch(target).addCards(cards);
	}

}
