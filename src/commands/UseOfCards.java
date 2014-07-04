package commands;

import java.util.ArrayList;

import player.PlayerComplete;
import cards.Card;
import core.Game;
import core.PlayerInfo;

/**
 * use of cards by a player, used cards are displayed in the disposal area until an event
 * is finished, then they are recycled by deck
 * @author Harry
 *
 */
public class UseOfCards extends Command
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1407461225573885948L;
	private PlayerInfo source;
	private String nameOfCardEffective;
	private ArrayList<Card> cardsUsed;
	
	/**
	 * Simple setup of useofcards, in which card used = card effective
	 * @param source
	 * @param cardUsed
	 */
	public UseOfCards(PlayerInfo source,Card cardUsed,Command next)
	{
		super(next);
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
	public void ClientOperation(PlayerComplete player)
	{
		System.out.println(player.getName()+" UseOfCards ");
		if(player.equals(source))
		{
			player.useCards(cardsUsed);
			player.sendToServer(getNext());
		}
		else
			player.findMatch(source).useCards(cardsUsed);
		
	}
	@Override
	public void ServerOperation(Game framework) 
	{
		framework.getDeck().discardAll(cardsUsed);
		framework.sendToAllClients(this);
	}

	
}
