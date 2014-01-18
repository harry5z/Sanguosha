package update;

import java.util.ArrayList;

import player.PlayerOriginalClientComplete;
import core.Framework;
import core.PlayerInfo;
import equipments.Equipment;

public class LossOfEquipment implements Update
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6808896498802425889L;
	private PlayerInfo source;
	private Update next;
	private ArrayList<Equipment> equipments;
	
	public LossOfEquipment(PlayerInfo source,ArrayList<Equipment> equipments,Update next)
	{
		this.source = source;
		this.equipments = new ArrayList<Equipment>();
		for(Equipment e : equipments)
			this.equipments.add(e);
		this.next = next;
	}
	@Override
	public void frameworkOperation(Framework framework)
	{
		framework.getDeck().discardAll(equipments);
		framework.sendToAllClients(this);
	}
	@Override
	public void playerOperation(PlayerOriginalClientComplete player) 
	{
		System.out.println(player.getName()+" LossOfEquipment ");
		if(player.isEqualTo(source))
		{
			for(Equipment e : equipments)
				player.unequip(e.getType());
			player.sendToMaster(next);
		}
		else
		{
			for(Equipment e : equipments)
				player.findMatch(source).unequip(e.getType());
		}
	}
}
