package update;

import core.Player;

public class StageUpdate extends Update
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1512958754082503787L;
	public static final int TURN_START = 1;
	public static final int TURN_DECISION = 2;
	public static final int TURN_DRAW = 3;
	public static final int TURN_DEAL = 4;
	public static final int TURN_DISCARD = 5;
	public static final int TURN_END = 6;
	
	private Player source;
	private int stage;
	private boolean isBeginning;
	
	public StageUpdate(Player source,int stage,boolean isBeginning)
	{
		this.source = source;
		this.stage = stage;
		this.isBeginning = isBeginning;
	}
	public Player getSource()
	{
		return source;
	}
	public int getStage()
	{
		return stage;
	}
	public boolean isBeginning()
	{
		return isBeginning;
	}

	@Override
	public void playerOperation(Player player)
	{
		Player operator;
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
}
