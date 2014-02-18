package update;

import player.PlayerOriginalClientComplete;
import core.Framework;
import core.PlayerInfo;

public class RequireUseOfCardByName extends Update
{
	
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
	public void playerOperation(PlayerOriginalClientComplete player) {
		// TODO Auto-generated method stub
		
	}

}
