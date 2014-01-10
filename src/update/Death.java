package update;

import player.PlayerOriginalClientComplete;
import core.Framework;
import core.PlayerInfo;

/**
 * Death update
 * @author Harry
 *
 */
public class Death implements Update
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4321634819282332287L;
	private PlayerInfo victim;
	private Update next;
	public Death(PlayerInfo victim,Update next)
	{
		this.victim = victim;
		this.next = next;
	}
	@Override
	public void frameworkOperation(Framework framework) 
	{
		framework.sendToAllClients(this);
	}

	@Override
	public void playerOperation(PlayerOriginalClientComplete player)
	{
		System.out.println(player.getName()+" Death ");
		if(player.isEqualTo(victim))
		{
			player.kill();//kill victim
			player.sendToMaster(new DisposalOfCards(victim,player.getCardsOnHand(),next));//victim discards all cards
		}
		else
			player.findMatch(victim).kill();
	}

}
