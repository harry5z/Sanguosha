package commands;

import java.util.ArrayList;
import java.util.List;

import player.PlayerComplete;
import cards.Card;
import core.PlayerInfo;
import core.server.Game;

/**
 * Used for core to recycle cards
 * @author Harry
 *
 */
public class RecycleCards extends Command
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5036532704216531486L;
	private List<Card> cards;
	private PlayerInfo source;
	public RecycleCards(Command next, List<Card> cards, PlayerInfo source) 
	{
		super(next);
		this.cards = new ArrayList<Card>();
		this.cards.addAll(cards);
		this.source = source;
	}

	@Override
	public void ServerOperation(Game framework)
	{
		framework.getDeck().discardAll(cards);
		framework.sendToAllClients(this);
	}

	@Override
	public void ClientOperation(PlayerComplete player)
	{
		player.showCards(cards);
		if(player.equals(source))
			player.sendToServer(getNext());
	}

}
