package commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import player.PlayerComplete;
import cards.Card;
import core.PlayerInfo;
import core.server.Game;

public class DisposalOfCards extends Command
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3876124827025507624L;
	private PlayerInfo source;
	private List<Card> cards;
	private boolean random = false;

	public DisposalOfCards(PlayerInfo source,Card card,Command next)
	{
		super(next);
		this.source = source;
		this.cards = new ArrayList<Card>(1);
		cards.add(card);
	}
	/**
	 * dispose a random card from target's hand
	 * @param source
	 * @param next
	 */
	public DisposalOfCards(PlayerInfo source,Command next)
	{
		super(next);
		this.source = source;
		this.cards = new ArrayList<Card>(1);
		this.random = true;
	}
	public DisposalOfCards(PlayerInfo source,List<Card> cards,Command next)
	{
		super(next);
		this.source = source;
		this.cards = new ArrayList<Card>();
		for(Card c : cards)
			this.cards.add(c);
	}
	public void setSource(PlayerInfo source)
	{
		this.source = source;
	}
	public void addCard(Card card)
	{
		cards.add(card);
	}
	@Override
	public void ServerOperation(Game framework)
	{
		if(cards.size() != 0)
			framework.getDeck().discardAll(cards);
		framework.sendToAllClients(this);
	}
	@Override
	public void ClientOperation(PlayerComplete player) 
	{
		System.out.println(player.getName()+" DisposalOfCards ");
		if(random) //random card on hand
		{
			random = false;
			if(player.equals(source))
			{
				Random rand = new Random();
				Card c = player.getCardsOnHand().get(rand.nextInt(player.getHandCount()));
				cards.add(c);
				player.sendToServer(this);
			}
			return;
		}
		if(player.equals(source))
		{
			player.discardCards(cards);
			player.sendToServer(getNext());
		}
		else
			player.findMatch(source).discardCards(cards);
	}

}
