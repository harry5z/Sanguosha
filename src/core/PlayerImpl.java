package core;


public class PlayerImpl extends Player
{
	public static final int EMPEROR = 0;
	public static final int LOYALIST = 1;
	public static final int REBEL = 2;
	public static final int USURPER = 3;
	
	private boolean skipDetermine = false;
	private boolean skipDraw = false;
	private boolean skipDeal = false;
	private boolean skipDiscard = false;
	
	private int role;
	
	public PlayerImpl(String name,int position)
	{
		super(name,position);
	}

	public void setRole(int role)
	{
		this.role = role;
	}
	/**
	 * Emperor? Loyalist? Rebel? Usurper?
	 * @return Player's role
	 */
	public int getRole()
	{
		return role;
	}
}
