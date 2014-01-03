package core;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Hero class, consisting of all heroes
 * @author Harry
 *
 */
public abstract class Hero implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7101963032096808206L;
	public static final int WEI = 1;
	public static final int SHU = 2;
	public static final int WU = 3;
	public static final int QUN = 4;
	
	public static final int MALE = 1;
	public static final int FEMALE = 2;
	
	private String name;//unique for every hero
	private int force;// WEI/SHU/WU/QUN
	private int sex;//MALE / FEMALE
	private int healthLimit;//usually 3 or 4
	private int cardsOnHandLimit;//usually equal to current health, unless changed by skills
	private ArrayList<Skill> skills;//not implememented yet
	
	public Hero(int healthLimit, int force, int sex, String name)
	{
		this.name = name;
		this.force = force;
		this.sex = sex;
		this.healthLimit = healthLimit;
		this.cardsOnHandLimit = healthLimit;
		this.skills = new ArrayList<Skill>();
	}

	public String getName()
	{
		return name;
	}
	public void changeHealthLimitTo(int n)
	{
		healthLimit = n;
	}
	public void changeHealthLimitBy(int n)
	{
		healthLimit += n;
	}
	public int getHealthLimit()
	{
		return healthLimit;
	}

	public void changeCardLimitTo(int n)
	{
		cardsOnHandLimit = n;
	}
	public void changeCardLimitBy(int n)
	{
		cardsOnHandLimit += n;
	}
	public int getCardOnHandLimit()
	{
		return cardsOnHandLimit;
	}



	public void changeSex(int n){sex = n;}
	public int getSex(){return sex;}
	public void changeForce(int n){force = n;}
	public int getForce(){return force;}




	
	
	
	
	
	
	
}
