package update;

import java.util.ArrayList;
import java.util.List;

import player.PlayerClientComplete;
import cards.Card;
import core.Framework;
import core.PlayerInfo;

/**
 * Used for core to recycle cards
 * @author Harry
 *
 */
public class RecycleCards extends Update
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5036532704216531486L;
	private List<Card> cards;
	private PlayerInfo source;
	public RecycleCards(Update next, List<Card> cards, PlayerInfo source) 
	{
		super(next);
		this.cards = new ArrayList<Card>();
		this.cards.addAll(cards);
		this.source = source;
	}

	@Override
	public void frameworkOperation(Framework framework)
	{
		framework.getDeck().discardAll(cards);
		framework.sendToAllClients(this);
	}

	@Override
	public void playerOperation(PlayerClientComplete player)
	{
		player.showCards(cards);
		if(player.matches(source))
			player.sendToMaster(getNext());
	}

}
