package update;

import java.util.ArrayList;

import net.Client;
import core.Card;
import core.Player;
import core.Update;

public class UseOfCards extends Update
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1407461225573885948L;
	private Player source;
	private String nameOfCardEffective;
	private ArrayList<Card> cardsUsed;
	
	public UseOfCards(Player source)
	{
		this.source = source;
	}
	/**
	 * Simple setup of useofcards, in which card used = card effective
	 * @param source
	 * @param cardUsed
	 */
	public UseOfCards(Player source,Card cardUsed)
	{
		this.source = source;
		this.nameOfCardEffective = cardUsed.getName();
		cardsUsed = new ArrayList<Card>(1);
		cardsUsed.add(cardUsed);
	}
	public void setSource(Player player)
	{
		source = player;
	}
	public Player getSource()
	{
		return source;
	}
	public String getNameOfCardEffective()
	{
		return nameOfCardEffective;
	}
	public ArrayList<Card> getCardsUsed()
	{
		return cardsUsed;
	}

	@Override
	public void playerOperation(Player player)
	{
		if(player.equals(source))
			player.useCards(cardsUsed);
		else
			player.findMatch(source).useCards(cardsUsed);
		
	}

	
}
