package update;

import java.util.ArrayList;

import net.Master;
import core.Player;
import core.Update;

public class NextStage extends Update
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3750924298403873814L;
	private StageUpdate currentStage;
	public NextStage(StageUpdate current)
	{
		currentStage = current;
	}
	@Override
	public void frameworkOperation(Master master)
	{
		if(currentStage.isBeginning())
		{
			master.sendToAllClients(new StageUpdate(currentStage.getSource(),currentStage.getStage(),false));
			return;
		}
		int stage = currentStage.getStage();
		if(stage != StageUpdate.TURN_END)
		{
			master.sendToAllClients(new StageUpdate(currentStage.getSource(),stage+1,true));
			return;
		}
		ArrayList<Player> players = master.getFramework().getPlayers();
		Player currentPlayer = currentStage.getSource();
		Player next = null;
		for(Player p : players)
		{
			if(!p.isAlive())
				continue;
			if(next == null && p.getPosition() > currentPlayer.getPosition())
			{
				next = p;
				continue;
			}
			if(p.getPosition() > currentPlayer.getPosition() && p.getPosition() < next.getPosition())
				next = p;
		}
		if(next == null)
		{
			for(Player p : players)
			{
				if(!p.isAlive())
					continue;
				if(next == null && p.getPosition() < currentPlayer.getPosition())
				{
					next = p;
					continue;
				}
				if(p.getPosition() < next.getPosition())
					next = p;
			}
		}
		if(next == null)
			System.err.println("Master: Next player not found");
		master.sendToAllClients(new StageUpdate(next,StageUpdate.TURN_START,true));
	}
	@Override
	public void playerOperation(Player player) 
	{
		
	}



}
