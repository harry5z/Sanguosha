package basics;

import core.Basic;


public class Peach extends Basic
{
	public static final String PEACH = "Peach";
	public Peach(int num, int suit)
	{
		super(num, suit);
	}
	@Override
	public String getName()
	{
		return PEACH;
	}
}
