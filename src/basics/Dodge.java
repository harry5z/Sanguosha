package basics;

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
}
