package cards.basics;

import player.PlayerOriginalClientComplete;
import update.Update;
import update.operations.Operation;

public class Dodge extends Basic
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7923623178052220181L;
	public static final String DODGE = "Dodge";
	public Dodge(int num, int suit)
	{
		super(num, suit);
	}
	public Dodge()
	{
		
	}
	@Override
	public String getName()
	{
		return DODGE;
	}
	@Override
	public boolean isActivatableBy(PlayerOriginalClientComplete player) 
	{
		return false;
	}
	@Override
	public Operation onActivatedBy(PlayerOriginalClientComplete player,Update next) 
	{
		System.err.println("Dodge: should not be executed");
		return null;
	}

}
