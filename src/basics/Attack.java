package basics;

import core.Basic;


public class Attack extends Basic
{
	private int element = 0;//0.Normal 1.Fire 2.Thunder
	public static final int NORMAL = 0;
	public static final int FIRE = 1;
	public static final int THUNDER = 2;
	public Attack(int e, int num, int suit)
	{
		super(num, suit);
		element = e;
	}
	public int getElement()
	{
		return element;
	}
	@Override
	public String getName()
	{
		return "Attack";
	}
}
