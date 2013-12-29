package basics;

import core.Basic;
import core.Player;


public class Peach extends Basic
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4681237057056984060L;
	public static final String PEACH = "Peach";
	private Player source;
	public Peach(int num, int suit)
	{
		super(num, suit);
	}
	@Override
	public String getName()
	{
		return PEACH;
	}
	@Override
	public void onActivatedBy(Player player) 
	{
		source = player;
		player.getUpdateStack().push(this);
	}
	@Override
	public void playerOperation(Player player) 
	{
		if(player.equals(source))
			player.changeHealthCurrentBy(1);
		else
			player.findMatch(source).changeHealthCurrentBy(1);
	}
	@Override
	public boolean isActivatableBy(Player player) 
	{
		if(player.getHealthCurrent() < player.getHealthLimit())
			return true;
		else
			return false;
	}
}
