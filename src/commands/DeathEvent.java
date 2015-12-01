package commands;

import core.player.PlayerComplete;
import core.player.PlayerInfo;
import core.server.game.Game;

/**
 * The event of the death of a player
 * @author Harry
 *
 */
public class DeathEvent extends Command
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
	private int stage;
	public DeathEvent(PlayerInfo turnPlayer,PlayerInfo killer,PlayerInfo victim,Command next)
	{
		super(next);
		this.turnPlayer = turnPlayer;
		this.currentPlayer = turnPlayer;
		this.killer = killer;
		this.victim = victim;
		stage = BEFORE;
	}
	@Override
	public void ServerOperation(Game framework) 
	{
		framework.sendToAllClients(this);
	}

	@Override
	public void ClientOperation(PlayerComplete player) 
	{
		System.out.println(player.getName()+" DeathEvent "+stage);
		if(stage == BEFORE)
		{
			if(player.equals(currentPlayer))//for future skills
			{
				sendToNextPlayer(player);
			}
		}
		else if(stage == AFTER)
		{
			if(player.equals(currentPlayer))//for future skills
			{
				if(player.equals(victim))//die
				{
					stage++;
//					currentPlayer = player.getNextPlayerAlive();
//					if(currentPlayer.getPosition() == turnPlayer.getPosition())//circle complete
//					{
//						stage++;
//					}
					player.sendToServer(new Death(victim,this));
				}
				else
					sendToNextPlayer(player);
			}
		}
		else if(stage == CONSEQUENCE)//future implementation
		{
			if(player.equals(killer))
				player.sendToServer(getNext());
		}
	}
	private void sendToNextPlayer(PlayerComplete player)
	{
		currentPlayer = player.getNextPlayerAlive();
		if(currentPlayer.getPosition() == turnPlayer.getPosition())//circle complete
		{
			stage++;
		}
		player.sendToServer(this);
	}
}
