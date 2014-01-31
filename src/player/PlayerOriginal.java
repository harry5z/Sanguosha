package player;


/**
 * implementation of player of Original Sanguosha
 * @author Harry
 *
 */
public abstract class PlayerOriginal extends Player
{
	public static final int EMPEROR = 0;
	public static final int LOYALIST = 1;
	public static final int REBEL = 2;
	public static final int USURPER = 3;
	
	private int role;
	
	public PlayerOriginal(String name)
	{
		super(name);
	}
	public PlayerOriginal(String name, int position)
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
