package commands;

import java.util.List;

import player.PlayerComplete;
import cards.Card;
import core.PlayerInfo;
import core.server.Game;

/**
 * player's request of cards from deck
 * @author Harry
 *
 */
public class DrawCardsFromDeck extends Command
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3227087424276735239L;
	private int amount;
	private List<Card> cards;
	private int deckSize;
	private PlayerInfo source;
	
	public DrawCardsFromDeck(PlayerInfo source, int amount,Command next)
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
	public void ServerOperation(Game framework) 
	{
		cards = framework.getDeck().drawMany(amount);
		deckSize = framework.getDeck().getDeckSize();
		framework.sendToAllClients(this);
	}
	@Override
	public void ClientOperation(PlayerComplete player) 
	{
		player.setDeckSize(deckSize);
		if(player.equals(source))
		{
			player.addCards(cards);
			if(getNext() != null)
				player.sendToServer(getNext());
		}
		else
		{
			player.findMatch(source).addCards(cards);
		}
	}
}
