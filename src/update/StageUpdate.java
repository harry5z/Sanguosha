package update;

import player.PlayerOriginalClientComplete;
import core.Card;
import core.Event;
import core.Framework;
import core.Player;
import core.PlayerInfo;
import core.Update;

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

	@Override
	public void playerOperation(PlayerOriginalClientComplete player)
	{
		player.setCurrentStage(this);
		
		if(player.equals(source))
		{
			
			if(stage == TURN_DRAW)
			{
				player.drawCards();
			}
			else if(stage == TURN_DEAL)
			{
				player.startDealing();
			}
		}
	}
	@Override
	public void frameworkOperation(Framework framework) 
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void nextStep() 
	{
		stage++;
	}
}
