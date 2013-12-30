package core;

import java.io.Serializable;

import player.*;

public interface Update extends Serializable
{
	/**
	 * behavior of master-side operation
	 * @param framework
	 */
	public void frameworkOperation(Framework framework);
	
	public void playerOperation(PlayerOriginalClientComplete player);
	

}
