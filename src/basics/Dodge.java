package basics;

import core.Basic;


public class Dodge extends Basic
{
	public Dodge(int num, int suit)
	{
		super(num, suit);
	}
	@Override
	public String getName()
	{
		return "Dodge";
	}
}
