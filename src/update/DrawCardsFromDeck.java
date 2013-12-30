package update;

import net.Master;
import player.PlayerOriginalClientComplete;
import core.Framework;
import core.Player;
import core.Update;

public class DrawCardsFromDeck implements Update
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3372389685603159424L;
	private Player source;
	private int amount;
	public DrawCardsFromDeck(Player source,int amount)
	{
		this.source = source;
		this.amount = amount;
	}
	public Player getSource()
	{
		return source;
	}
	public int getAmount()
	{
		return amount;
	}
	@Override
	public void frameworkOperation(Framework framework)
	{
		framework.sendToAllClients(new ReceiveCardsFromDeck(source,framework.getDeck().drawMany(amount)));
	}
	@Override
	public void playerOperation(PlayerOriginalClientComplete player) 
	{
		// TODO Auto-generated method stub
		
	}
}
