package update;

import java.util.ArrayList;

import core.Card;
import core.Master;
import core.Player;

public class DisposalOfCards extends Update
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3876124827025507624L;
	private Player source;
	private ArrayList<Card> cards;
	public DisposalOfCards(Player source)
	{
		this.source = source;
	}
	public DisposalOfCards(Player source,Card card)
	{
		this.source = source;
		this.cards = new ArrayList<Card>(1);
		cards.add(card);
	}
	public void setSource(Player source)
	{
		this.source = source;
	}
	public void addCard(Card card)
	{
		cards.add(card);
	}
	public void setCards(ArrayList<Card> cards)
	{
		this.cards = cards;
	}
	@Override
	public void masterOperation(Master master)
	{
		master.getFramework().getDeck().discardAll(cards);
		super.masterOperation(master);
	}
	@Override
	public void playerOperation(Player player) 
	{
		if(player.equals(source))
			player.discardCards(cards);
		else
			player.findMatch(player).discardCards(cards);
	}

}
