package update;

import java.util.List;

import player.PlayerClientComplete;
import cards.Card;
import core.Framework;
import core.PlayerInfo;

/**
 * player's request of cards from deck
 * @author Harry
 *
 */
public class DrawCardsFromDeck extends Update
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3227087424276735239L;
	private int amount;
	private List<Card> cards;
	private int deckSize;
	private PlayerInfo source;
	
	public DrawCardsFromDeck(PlayerInfo source, int amount,Update next)
	{
		super(next);
		this.source = source;
		this.amount = amount;
	}
	public DrawCardsFromDeck(PlayerInfo source, List<Card> cards,int size)
	{
		super(null);
		this.source = source;
		this.cards = cards;
		this.deckSize = size;
	}
	@Override
	public void frameworkOperation(Framework framework) 
	{
		cards = framework.getDeck().drawMany(amount);
		deckSize = framework.getDeck().getDeckSize();
		framework.sendToAllClients(this);
	}
	@Override
	public void playerOperation(PlayerClientComplete player) 
	{
		player.setDeckSize(deckSize);
		if(player.matches(source))
		{
			player.addCards(cards);
			if(getNext() != null)
				player.sendToMaster(getNext());
		}
		else
		{
			player.findMatch(source).addCards(cards);
		}
	}
}
