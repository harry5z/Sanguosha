package update;

import java.util.ArrayList;

import player.PlayerOriginalClientComplete;
import net.Master;
import core.Card;
import core.Framework;
import core.Player;
import core.PlayerInfo;
import core.Update;

public class DisposalOfCards implements Update
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3876124827025507624L;
	private PlayerInfo source;
	private ArrayList<Card> cards;
	private Update next;
	public DisposalOfCards(PlayerInfo source)
	{
		this.source = source;
	}
	public DisposalOfCards(PlayerInfo source,Card card)
	{
		this.source = source;
		this.cards = new ArrayList<Card>(1);
		cards.add(card);
	}
	public DisposalOfCards(PlayerInfo source,ArrayList<Card> cards,Update next)
	{
		this.source = source;
		this.cards = new ArrayList<Card>();
		this.next = next;
		for(Card c : cards)
			this.cards.add(c);
	}
	public void setNext(Update next)
	{
		this.next = next;
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
		if(player.isEqualTo(source))
		{
			player.discardCards(cards);
			player.sendToMaster(next);
		}
		else
			player.findMatch(source).discardCards(cards);
	}

}
