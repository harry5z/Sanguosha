package utils;

import player.PlayerComplete;
import player.PlayerOriginal;
import core.PlayerInfo;

public class OperationUtil 
{
	/*
	 * not to be initialized
	 */
	private OperationUtil() {}
	
	/**
	 * invoked when new target is selected
	 * @param operator
	 * @param currentTarget
	 * @param newTarget
	 * @return
	 */
	public static PlayerInfo selectTarget(PlayerComplete operator, PlayerInfo currentTarget, PlayerOriginal newTarget)
	{
		if(currentTarget == null)//select target
		{
			currentTarget = newTarget.getPlayerInfo();
			operator.getGameListener().setTargetSelected(currentTarget, true);//target selected
			operator.getGameListener().setConfirmEnabled(true);//can confirm
		}
		else
		{
			if(newTarget.equals(currentTarget))//cancel
			{
				operator.getGameListener().setTargetSelected(currentTarget, true);;//no target
				operator.getGameListener().setConfirmEnabled(false);//cannot confirm
				currentTarget = null;
			}
			else//change
			{
				operator.getGameListener().setTargetSelected(currentTarget, false);
				currentTarget = newTarget.getPlayerInfo();
				operator.getGameListener().setTargetSelected(currentTarget, true);
			}
		}
		return currentTarget;
	}
}
