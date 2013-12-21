package core;

import java.util.ArrayList;

public interface Player 
{
	public static final int EMPEROR = 0;
	public static final int LOYALIST = 1;
	public static final int REBEL = 2;
	public static final int USURPER = 3;
	/**
	 * Emperor? Loyalist? Rebel? Usurper?
	 * @return Player's role
	 */
	public int getRole();
	/**
	 * which hero this player is playing?
	 * @return current hero
	 */
	public Hero getHero();

	
}
