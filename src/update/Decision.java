package update;

import player.PlayerClientComplete;
import update.operations.decision_operations.DecisionOperation;
import cards.Card;
import core.Framework;
import core.PlayerInfo;

public class Decision extends Update
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3876772467662191549L;
	private PlayerInfo source;
	private Card decision;
	
	public Decision(PlayerInfo source, DecisionOperation next)
	{
		super(next);
		this.source = source;
	}
	@Override
	public void frameworkOperation(Framework framework)
	{
		decision = framework.getDeck().draw();
		framework.sendToAllClients(this);
	}

	@Override
	public void playerOperation(PlayerClientComplete player) 
	{
		player.showCard(decision);
		if(player.matches(source))
		{
			((DecisionOperation)getNext()).setResult(decision);
			player.sendToMaster(getNext());
		}
	}

}
