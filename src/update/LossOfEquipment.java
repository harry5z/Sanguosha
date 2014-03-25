package update;

import java.util.ArrayList;
import java.util.List;

import player.PlayerClientComplete;
import cards.equipments.Equipment;
import cards.equipments.shields.Shield;
import core.Framework;
import core.PlayerInfo;

/**
 * Player 
 * @author Harry
 *
 */
public class LossOfEquipment extends Update
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6808896498802425889L;
	private PlayerInfo source;
	private List<Equipment> equipments;
	
	public LossOfEquipment(PlayerInfo source,Update next,Equipment... equipments)
	{
		super(next);
		this.source = source;
		this.equipments = new ArrayList<Equipment>();
		for(Equipment e : equipments)
			this.equipments.add(e);
	}
	@Override
	public void frameworkOperation(Framework framework)
	{
		framework.getDeck().discardAll(equipments);
		framework.sendToAllClients(this);
	}
	@Override
	public void playerOperation(PlayerClientComplete player) 
	{
		System.out.println(player.getName()+" LossOfEquipment ");
		if(player.matches(source))
		{
			for(Equipment e : equipments)
			{
				player.showCard(e);
				if(e instanceof Shield)
					((Shield)e).onUnequipped(player, this);
			}
			
			player.sendToMaster(getNext());
		}
		else
		{
			player.findMatch(source).showCards(equipments);
		}
	}
}
