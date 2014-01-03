package update;

import player.PlayerOriginalClientComplete;
import core.Event;
import core.Framework;
import core.PlayerInfo;

public class StageUpdate implements Event
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1512958754082503787L;
	public static final int TURN_START_BEGINNING = 1;
	public static final int TURN_START = 2;
	public static final int TURN_DECISION_BEGINNING = 3;
	public static final int TURN_DECISION = 4;
	public static final int TURN_DRAW = 5;
	public static final int TURN_DEAL_BEGINNING = 6;
	public static final int TURN_DEAL = 7;
	public static final int TURN_DISCARD_BEGINNING = 8;
	public static final int TURN_DISCARD = 9;
	public static final int TURN_DISCARD_END = 10;
	public static final int TURN_END = 11;
	
	private PlayerInfo source;
	private int stage;
	
	public StageUpdate(PlayerInfo source,int stage)
	{
		this.source = source;
		this.stage = stage;
	}
	public PlayerInfo getSource()
	{
		return source;
	}
	public int getStage()
	{
		return stage;
	}
	public void nextStage(PlayerOriginalClientComplete player)
	{
		if(stage != TURN_END)
			stage++;
		else
		{
			source = player.getNextPlayerAlive();
			stage = TURN_START_BEGINNING;
		}
	}
	@Override
	public void playerOperation(PlayerOriginalClientComplete player)
	{
		player.setCurrentStage(this);
		player.clearDisposalArea();
		if(player.isEqualTo(source))
		{
			
			if(stage == TURN_DRAW)
			{
				player.drawCards();
			}
			else if(stage == TURN_DEAL)
			{
				player.startDealing();
			}
			else if(stage == TURN_DISCARD)
				player.turnDiscard();
			else if(stage == TURN_END)
				player.endTurn();
			else
			{
				stage++;
				player.sendToMaster(this);
			}
		}
		else
			System.out.println(player.getName()+" is watching "+source.getName()+" executing stage "+stage);
	}
	@Override
	public void frameworkOperation(Framework framework) 
	{
		framework.sendToAllClients(this);
	}
	@Override
	public void nextStep() 
	{
		stage++;
	}
}
