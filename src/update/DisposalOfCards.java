package update;

import java.util.ArrayList;

import cards.Card;
import player.PlayerOriginalClientComplete;
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

	public DisposalOfCards(PlayerInfo source,Card card,Update next)
	{
		super(next);
		this.source = source;
		this.cards = new ArrayList<Card>(1);
		cards.add(card);
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
		framework.getDeck().discardAll(cards);
		framework.sendToAllClients(this);
	}
	@Override
	public void playerOperation(PlayerOriginalClientComplete player) 
	{
		System.out.println(player.getName()+" DisposalOfCards ");
		if(player.matches(source))
		{
			player.discardCards(cards);
			player.sendToMaster(getNext());
		}
		else
			player.findMatch(source).discardCards(cards);
	}

}
