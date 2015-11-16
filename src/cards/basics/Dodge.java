package cards.basics;

import player.PlayerComplete;

import commands.Command;
import commands.operations.Operation;

public class Dodge extends Basic
{

	private static final long serialVersionUID = -7923623178052220181L;
	
	public static final String DODGE = "Dodge";
	
	public Dodge(int num, Suit suit)
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
	public boolean isActivatableBy(PlayerComplete player) 
	{
		return false;
	}
	@Override
	public Operation onActivatedBy(PlayerComplete player,Command next) 
	{
		System.err.println("Dodge: should not be executed");
		return null;
	}

}
