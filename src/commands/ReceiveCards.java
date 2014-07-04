package commands;

import java.util.ArrayList;
import java.util.List;

import player.PlayerComplete;
import cards.Card;
import core.Game;
import core.PlayerInfo;

public class ReceiveCards extends Command
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6958441460938662725L;
	private PlayerInfo receiver;
	private List<Card> cards;
	
	public ReceiveCards(Command next, PlayerInfo receiver, Card card) 
	{
		super(next);
		this.receiver = receiver;
		cards = new ArrayList<Card>(1);
		cards.add(card);
	}

	@Override
	public void ServerOperation(Game framework) 
	{
		framework.sendToAllClients(this);
	}

	@Override
	public void ClientOperation(PlayerComplete player) 
	{
		if(player.equals(receiver))
		{
			player.addCards(cards);
			player.sendToServer(getNext());
		}
		else
			player.findMatch(receiver).addCards(cards);
	}

}
