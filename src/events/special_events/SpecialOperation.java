package events.special_events;

import player.PlayerOriginal;
import player.PlayerOriginalClientComplete;
import core.Card;
import core.Framework;
import core.Operation;
import core.Update;

public abstract class SpecialOperation implements Operation
{
	private boolean neutralizable;
	private boolean neutralized;
	private Update next;
	private Card special;
	
	public SpecialOperation(Update next)
	{
		neutralizable = true;
		neutralized = false;
		this.next = next;
	}
	protected void neutralize()
	{
		neutralized = !neutralized;
	}
	public void setNeutralizable(boolean neutralizable)
	{
		this.neutralizable = neutralizable;
	}
	public boolean isNeutralizable()
	{
		return neutralizable;
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

	@Override
	public void onPlayerSelected(PlayerOriginalClientComplete operator,
			PlayerOriginal player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCardSelected(PlayerOriginalClientComplete operator, Card card) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCancelledBy(PlayerOriginalClientComplete player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConfirmedBy(PlayerOriginalClientComplete player) {
		// TODO Auto-generated method stub
		
	}

}
