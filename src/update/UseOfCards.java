package update;

import java.util.ArrayList;

import player.PlayerOriginalClientComplete;
import core.Card;
import core.Framework;
import core.PlayerInfo;
import core.Update;

public class UseOfCards implements Update
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1407461225573885948L;
	private PlayerInfo source;
	private String nameOfCardEffective;
	private ArrayList<Card> cardsUsed;
	
	public UseOfCards(PlayerInfo source)
	{
		this.source = source;
	}
	/**
	 * Simple setup of useofcards, in which card used = card effective
	 * @param source
	 * @param cardUsed
	 */
	public UseOfCards(PlayerInfo source,Card cardUsed)
	{
		this.source = source;
		this.nameOfCardEffective = cardUsed.getName();
		cardsUsed = new ArrayList<Card>(1);
		cardsUsed.add(cardUsed);
	}
	public void setSource(PlayerInfo player)
	{
		source = player;
	}
	public PlayerInfo getSource()
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
	public void playerOperation(PlayerOriginalClientComplete player)
	{
		if(player.equals(source))
			player.useCards(cardsUsed);
		else
			player.findMatch(source).useCards(cardsUsed);
		
	}
	@Override
	public void frameworkOperation(Framework framework) 
	{
		framework.getDeck().discardAll(cardsUsed);
		framework.sendToAllClients(this);
	}

	
}
