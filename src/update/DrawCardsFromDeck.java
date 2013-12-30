package update;

import java.util.ArrayList;

import player.PlayerOriginalClientComplete;
import core.Card;
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
	
	public DrawCardsFromDeck(PlayerInfo source, int amount)
	{
		this.source = source;
		this.amount = amount;
	}

	@Override
	public void frameworkOperation(Framework framework) 
	{
		cards = framework.getDeck().drawMany(amount);
		framework.findMatch(source).addCards(cards);
		framework.sendToAllClients(this);
	}
	@Override
	public void playerOperation(PlayerOriginalClientComplete player) 
	{
		if(player.equals(source))
			player.addCards(cards);
		else
			player.findMatch(source).addCards(cards);
	}

}
