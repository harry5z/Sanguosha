package update;

import cards.Card;
import player.PlayerOriginalClientComplete;
import update.operations.decision_operations.DecisionOperation;
import core.Framework;
import core.PlayerInfo;

public class Decision implements Update
{

	private PlayerInfo source;
	private Card decision;
	private DecisionOperation next;
	
	public Decision(PlayerInfo source, DecisionOperation next)
	{
		this.source = source;
		this.next = next;
	}
	@Override
	public void frameworkOperation(Framework framework)
	{
		decision = framework.getDeck().draw();
		framework.sendToAllClients(this);
	}

	@Override
	public void playerOperation(PlayerOriginalClientComplete player) 
	{
		player.showCard(decision);
		if(player.matches(source))
		{
			next.setResult(decision);
			player.sendToMaster(next);
		}
	}

}
