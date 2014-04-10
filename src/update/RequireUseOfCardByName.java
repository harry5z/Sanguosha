package update;

import player.PlayerClientComplete;
import core.Framework;
import core.PlayerInfo;

public class RequireUseOfCardByName extends Update
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4679312323429905803L;
	private PlayerInfo source;
	private PlayerInfo target;
	private String cardName;
	
	public RequireUseOfCardByName(PlayerInfo source,PlayerInfo target,String cardName,Update next)
	{
		super(next);
		this.source = source;
		this.target = target;
		this.cardName = cardName;
	}
	@Override
	public void frameworkOperation(Framework framework) 
	{
		framework.sendToAllClients(this);
	}

	public void setCardUsed(boolean used)
	{
		
	}
	@Override
	public void playerOperation(PlayerClientComplete player) {
		// TODO Auto-generated method stub
		
	}

}
