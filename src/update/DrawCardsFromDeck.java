package update;

import java.util.ArrayList;

import player.PlayerOriginalClientComplete;
import core.Card;
import core.Event;
import core.Framework;
import core.Player;
import core.PlayerInfo;
import core.Update;

public class DrawCardsFromDeck implements Update
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3227087424276735239L;
	private int amount;
	private ArrayList<Card> cards;
	private PlayerInfo source;
	private Event nextEvent;
	
	public DrawCardsFromDeck(PlayerInfo source, int amount,Event next)
	{
		this.source = source;
		this.amount = amount;
		nextEvent = next;
	}
	public DrawCardsFromDeck(PlayerInfo source, ArrayList<Card> cards)
	{
		this.source = source;
		this.cards = cards;
		nextEvent = null;
	}
	@Override
	public void frameworkOperation(Framework framework) 
	{
		cards = framework.getDeck().drawMany(amount);

		framework.sendToAllClients(this);
	}
	@Override
	public void playerOperation(PlayerOriginalClientComplete player) 
	{
		if(player.isEqualTo(source))
		{
			System.out.println("myself adding cards");
			player.addCards(cards);
			if(nextEvent != null)
				player.sendToMaster(nextEvent);
		}
		else
		{
			System.out.println("Player "+source.getPosition()+" adding cards");
			player.findMatch(source).addCards(cards);
		}
			
	}

}
