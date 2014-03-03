package update;

import java.util.ArrayList;
import java.util.Random;

import player.PlayerClientComplete;
import cards.Card;
import core.Framework;
import core.PlayerInfo;

public class DisposalOfCards extends Update
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3876124827025507624L;
	private PlayerInfo source;
	private ArrayList<Card> cards;
	private boolean random = false;

	public DisposalOfCards(PlayerInfo source,Card card,Update next)
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
	public DisposalOfCards(PlayerInfo source,Update next)
	{
		super(next);
		this.source = source;
		this.cards = new ArrayList<Card>(1);
		this.random = true;
	}
	public DisposalOfCards(PlayerInfo source,ArrayList<Card> cards,Update next)
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
	public void frameworkOperation(Framework framework)
	{
		if(cards.size() != 0)
			framework.getDeck().discardAll(cards);
		framework.sendToAllClients(this);
	}
	@Override
	public void playerOperation(PlayerClientComplete player) 
	{
		System.out.println(player.getName()+" DisposalOfCards ");
		if(random) //random card on hand
		{
			random = false;
			if(player.matches(source))
			{
				Random rand = new Random();
				Card c = player.getCardsOnHand().get(rand.nextInt(player.getCardsOnHandCount()));
				cards.add(c);
				player.sendToMaster(this);
			}
			return;
		}
		if(player.matches(source))
		{
			player.discardCards(cards);
			player.sendToMaster(getNext());
		}
		else
			player.findMatch(source).discardCards(cards);
	}

}
