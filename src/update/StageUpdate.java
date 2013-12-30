package update;

import player.PlayerOriginalClientComplete;
import core.Card;
import core.Framework;
import core.Player;
import core.PlayerInfo;
import core.Update;

public class StageUpdate implements Update
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
		Player operator;
		player.setCurrentStage(this);
		if(player.equals(source))
			operator = player;
		else
			operator = player.findMatch(source);
			
		if(stage == TURN_DRAW && isBeginning)
		{
			operator.drawCards();
		}
		else if(stage == TURN_DEAL && isBeginning)
		{
			operator.startDealing();
		}
	}
	@Override
	public void frameworkOperation(Framework framework) 
	{
		// TODO Auto-generated method stub
		
	}
}
