package player;


/**
 * implementation of player of Original Sanguosha
 * @author Harry
 *
 */
public abstract class PlayerOriginal extends Player
{
	
	public enum Role
	{
		EMPEROR, LOYALIST, REBEL, USURPER
	}
	private Role role;
	
	public PlayerOriginal(String name, int position)
	{
		super(name,position);
	}
	public void setRole(Role role)
	{
		this.role = role;
	}
	/**
	 * Emperor? Loyalist? Rebel? Usurper?
	 * @return Player's role
	 */
	public Role getRole()
	{
		return role;
	}
}
