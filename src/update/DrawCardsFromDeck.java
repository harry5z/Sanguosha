package update;

import core.Master;
import core.Player;

public class DrawCardsFromDeck extends Update
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
	public void masterOperation(Master master)
	{
		master.sendToAllClients(new ReceiveCardsFromDeck(source,master.getFramework().getDeck().drawMany(amount)));
	}
	@Override
	public void playerOperation(Player player) 
	{
		// TODO Auto-generated method stub
		
	}
}
