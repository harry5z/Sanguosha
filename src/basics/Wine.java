package basics;

import core.Basic;


public class Wine extends Basic
{
	public static final String WINE = "Wine";
	public Wine(int num, int suit)
	{
		super(num, suit);
	}
	@Override
	public String getName()
	{
		return WINE;
	}
}
