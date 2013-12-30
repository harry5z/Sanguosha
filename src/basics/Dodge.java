package basics;

import player.PlayerOriginalClientComplete;
import core.Basic;

public class Dodge extends Basic
{
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
	public void onActivatedBy(PlayerOriginalClientComplete player) 
	{
		System.err.println("Dodge: should not be executed");
	}
	@Override
	public boolean isActivatableBy(PlayerOriginalClientComplete player) 
	{
		return false;
	}

}
