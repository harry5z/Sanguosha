package basics;

import core.Basic;
import core.Player;


public class Dodge extends Basic
{
	public static final String DODGE = "Dodge";
	private Player source;
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
	public void onActivatedBy(Player player) 
	{
		source = player;
		player.getUpdateStack().push(this);
	}
	@Override
	public void playerOperation(Player player) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onPlayerSelected(Player player) {
		// TODO Auto-generated method stub
		
	}
}
