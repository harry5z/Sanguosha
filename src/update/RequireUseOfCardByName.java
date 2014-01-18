package update;

import player.PlayerOriginalClientComplete;
import core.Framework;
import core.PlayerInfo;

public class RequireUseOfCardByName implements Update
{
	
	private PlayerInfo source;
	private PlayerInfo target;
	private String cardName;
	private Update next;
	
	public RequireUseOfCardByName(PlayerInfo source,PlayerInfo target,String cardName,Update next)
	{
		this.source = source;
		this.target = target;
		this.cardName = cardName;
		this.next = next;
	}
	@Override
	public void frameworkOperation(Framework framework) 
	{
		framework.sendToAllClients(this);
	}

	@Override
	public void playerOperation(PlayerOriginalClientComplete player) {
		// TODO Auto-generated method stub
		
	}

}
