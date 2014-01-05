package events.special_events;

import player.PlayerOriginal;
import player.PlayerOriginalClientComplete;
import core.Card;
import core.Framework;
import core.Operation;
import core.Update;

public abstract class SpecialOperation implements Operation
{
	private static final int BEFORE = 1;
	private static final int NEUTRALIZATION = 2;
	private static final int AFTER = 3;
	private boolean neutralizable;
	private boolean neutralized;
	private Update next;
	private int stage;
	
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
	protected Update getNext()
	{
		return next;
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
	public void playerOperation(PlayerOriginalClientComplete player)
	{
		if(stage == BEFORE)
			playerOpBefore(player);
		else if(stage == NEUTRALIZATION)
			playerOpNeutralization(player);
		else if (stage == AFTER)
			playerOpAfter(player);
	}
	protected abstract void playerOpBefore(PlayerOriginalClientComplete player);
	protected abstract void playerOpNeutralization(PlayerOriginalClientComplete player);
	protected abstract void playerOpAfter(PlayerOriginalClientComplete player);
}
