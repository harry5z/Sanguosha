package update;

import java.util.ArrayList;
import java.util.List;

import player.PlayerClientComplete;
import cards.Card;
import core.Framework;
import core.PlayerInfo;

public class ReceiveCards extends Update
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6958441460938662725L;
	private PlayerInfo receiver;
	private List<Card> cards;
	
	public ReceiveCards(Update next, PlayerInfo receiver, Card card) 
	{
		super(next);
		this.receiver = receiver;
		cards = new ArrayList<Card>(1);
		cards.add(card);
	}

	@Override
	public void frameworkOperation(Framework framework) 
	{
		framework.sendToAllClients(this);
	}

	@Override
	public void playerOperation(PlayerClientComplete player) 
	{
		if(player.matches(receiver))
		{
			player.addCards(cards);
			player.sendToMaster(getNext());
		}
		else
			player.findMatch(receiver).addCards(cards);
	}

}
