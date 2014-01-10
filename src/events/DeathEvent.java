package events;

import player.PlayerOriginalClientComplete;
import update.Death;
import update.Update;
import core.Framework;
import core.PlayerInfo;

/**
 * The event of the death of a player
 * @author Harry
 *
 */
public class DeathEvent implements Update
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6991457697750760518L;
	public static final int BEFORE = 1;
	public static final int AFTER = 2;
	public static final int CONSEQUENCE = 3;//if "rebel" is killed, killer draws 3 cards, etc.
	private PlayerInfo victim;
	private PlayerInfo killer;
	private PlayerInfo turnPlayer;//player who is playing the current turn
	private PlayerInfo currentPlayer;//player who is currently being inquired for skills
	private Update next;
	private int stage;
	public DeathEvent(PlayerInfo turnPlayer,PlayerInfo killer,PlayerInfo victim,Update next)
	{
		this.turnPlayer = turnPlayer;
		this.currentPlayer = turnPlayer;
		this.killer = killer;
		this.victim = victim;
		this.next = next;
		stage = BEFORE;
	}
	@Override
	public void frameworkOperation(Framework framework) 
	{
		framework.sendToAllClients(this);
	}

	@Override
	public void playerOperation(PlayerOriginalClientComplete player) 
	{
		System.out.println(player.getName()+" DeathEvent "+stage);
		if(stage == BEFORE)
		{
			if(player.isEqualTo(currentPlayer))//for future skills
			{
				sendToNextPlayer(player);
			}
		}
		else if(stage == AFTER)
		{
			if(player.isEqualTo(currentPlayer))//for future skills
			{
				if(player.isEqualTo(victim))//die
				{
					stage++;
//					currentPlayer = player.getNextPlayerAlive();
//					if(currentPlayer.getPosition() == turnPlayer.getPosition())//circle complete
//					{
//						stage++;
//					}
					player.sendToMaster(new Death(victim,this));
				}
				else
					sendToNextPlayer(player);
			}
		}
		else if(stage == CONSEQUENCE)//future implementation
		{
			if(player.isEqualTo(killer))
				player.sendToMaster(next);
		}
	}
	private void sendToNextPlayer(PlayerOriginalClientComplete player)
	{
		currentPlayer = player.getNextPlayerAlive();
		if(currentPlayer.getPosition() == turnPlayer.getPosition())//circle complete
		{
			stage++;
		}
		player.sendToMaster(this);
	}
}
