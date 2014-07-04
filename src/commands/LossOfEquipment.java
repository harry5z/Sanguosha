package commands;

import java.util.ArrayList;
import java.util.List;

import player.PlayerComplete;
import cards.equipments.Equipment;
import cards.equipments.shields.Shield;
import core.Game;
import core.PlayerInfo;

/**
 * Player 
 * @author Harry
 *
 */
public class LossOfEquipment extends Command
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6808896498802425889L;
	private PlayerInfo source;
	private List<Equipment> equipments;
	
	public LossOfEquipment(PlayerInfo source,Command next,Equipment... equipments)
	{
		super(next);
		this.source = source;
		this.equipments = new ArrayList<Equipment>();
		for(Equipment e : equipments)
			this.equipments.add(e);
	}
	@Override
	public void ServerOperation(Game framework)
	{
		framework.getDeck().discardAll(equipments);
		framework.sendToAllClients(this);
	}
	@Override
	public void ClientOperation(PlayerComplete player) 
	{
		System.out.println(player.getName()+" LossOfEquipment ");
		if(player.equals(source))
		{
			for(Equipment e : equipments)
			{
				player.showCard(e);
				if(e instanceof Shield)
					((Shield)e).onUnequipped(player, this);
			}
			
			player.sendToServer(getNext());
		}
		else
		{
			player.findMatch(source).showCards(equipments);
		}
	}
}
