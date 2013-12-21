package basics;

import core.Basic;


public class Wine extends Basic
{
	public Wine(int num, int suit)
	{
		super(num, suit);
	}
	@Override
	public String getName()
	{
		return "Wine";
	}
}
