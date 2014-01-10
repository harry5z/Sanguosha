package update;

import java.util.ArrayList;

import player.PlayerOriginalClientComplete;
import core.Card;
import core.Framework;
import core.PlayerInfo;

/**
 * player's request of cards from deck
 * @author Harry
 *
 */
public class DrawCardsFromDeck implements Update
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3227087424276735239L;
	private int amount;
	private ArrayList<Card> cards;
	private int deckSize;
	private PlayerInfo source;
	private Update nextEvent;
	
	public DrawCardsFromDeck(PlayerInfo source, int amount,Update next)
	{
		this.source = source;
		this.amount = amount;
		nextEvent = next;
	}
	public DrawCardsFromDeck(PlayerInfo source, ArrayList<Card> cards,int size)
	{
		this.source = source;
		this.cards = cards;
		this.deckSize = size;
		nextEvent = null;
	}
	@Override
	public void frameworkOperation(Framework framework) 
	{
		cards = framework.getDeck().drawMany(amount);
		deckSize = framework.getDeck().getDeckSize();
		framework.sendToAllClients(this);
	}
	@Override
	public void playerOperation(PlayerOriginalClientComplete player) 
	{
		player.setDeckSize(deckSize);
		if(player.isEqualTo(source))
		{
			player.addCards(cards);
			if(nextEvent != null)
				player.sendToMaster(nextEvent);
		}
		else
		{
			player.findMatch(source).addCards(cards);
		}
			
	}

}
