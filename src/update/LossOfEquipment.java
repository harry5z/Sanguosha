package update;

import java.util.ArrayList;

import cards.equipments.Equipment;
import player.PlayerClientComplete;
import core.Framework;
import core.PlayerInfo;

public class LossOfEquipment extends Update
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6808896498802425889L;
	private PlayerInfo source;
	private ArrayList<Equipment> equipments;
	
	public LossOfEquipment(PlayerInfo source,ArrayList<Equipment> equipments,Update next)
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
				player.unequip(e.getEquipmentType());
			player.sendToMaster(getNext());
		}
		else
		{
			for(Equipment e : equipments)
				player.findMatch(source).unequip(e.getEquipmentType());
		}
	}
}
