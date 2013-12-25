package update;

import core.Player;

public class RequireUseOfCardsByType extends SourceTargetAmount
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7389522337266545654L;
	private int type;
	public void setType(int type)
	{
		this.type = type;
	}
	public int getType()
	{
		return type;
	}
	@Override
	public void playerOperation(Player player) 
	{
		
	}

}
