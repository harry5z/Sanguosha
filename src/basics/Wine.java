package basics;

import core.Basic;
import core.Player;


public class Wine extends Basic
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5279754525954626716L;
	public static final String WINE = "Wine";
	private Player source;
	public Wine(int num, int suit)
	{
		super(num, suit);
	}
	@Override
	public String getName()
	{
		return WINE;
	}
	@Override
	public void onActivatedBy(Player player) 
	{
		source = player;
		//////////////////////
	}
	@Override
	public boolean isActivatableBy(Player player) 
	{
		if(player.getWineUsed() < player.getWineLimit() && !player.isWineUsed())
			return true;
		else
			return false;
	}
	@Override
	public void playerOperation(Player player) 
	{
		if(player.isDying())
		{
			if(player.equals(source))
				player.changeHealthCurrentBy(1);
			else
				player.findMatch(source).changeHealthCurrentBy(1);
		}
		else
		{
			if(player.equals(source))
				player.setWineUsed(player.getWineUsed()+1);
		}
	}
	@Override
	public void onPlayerSelected(Player player) {
		// TODO Auto-generated method stub
		
	}
}
