package update;

import java.util.ArrayList;

import cards.Card;
import player.PlayerOriginalClientComplete;
import core.Framework;
import core.PlayerInfo;

/**
 * use of cards by a player, used cards are displayed in the disposal area until an event
 * is finished, then they are recycled by deck
 * @author Harry
 *
 */
public class UseOfCards implements Update
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1407461225573885948L;
	private PlayerInfo source;
	private String nameOfCardEffective;
	private ArrayList<Card> cardsUsed;
	private Update nextEvent;
	
	public UseOfCards(PlayerInfo source)
	{
		this.source = source;
	}
	/**
	 * Simple setup of useofcards, in which card used = card effective
	 * @param source
	 * @param cardUsed
	 */
	public UseOfCards(PlayerInfo source,Card cardUsed,Update next)
	{
		this.source = source;
		this.nameOfCardEffective = cardUsed.getName();
		cardsUsed = new ArrayList<Card>(1);
		cardsUsed.add(cardUsed);
		nextEvent = next;
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
		System.out.println(player.getName()+" UseOfCards ");
		if(player.matches(source))
		{
			player.useCards(cardsUsed);
			player.sendToMaster(nextEvent);
		}
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
