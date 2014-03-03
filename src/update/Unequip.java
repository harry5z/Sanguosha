package update;

import player.PlayerClientComplete;
import cards.equipments.Equipment;
import cards.equipments.Equipment.EquipmentType;
import cards.equipments.shields.Shield;
import core.Framework;
import core.PlayerInfo;

public class Unequip extends Update
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5339794175888880779L;
	private PlayerInfo source;
	private Equipment equipment;
	
	public Unequip(PlayerInfo source, Update next, Equipment equipment) 
	{
		super(next);
		this.source = source;
		this.equipment = equipment;
	}

	@Override
	public void frameworkOperation(Framework framework) 
	{
		framework.getDeck().discard(equipment);
		framework.sendToAllClients(this);		
	}

	@Override
	public void playerOperation(PlayerClientComplete player)
	{
		if(player.matches(source))
		{
			player.unequip(equipment.getEquipmentType());
			player.showCard(equipment);
			if(equipment.getEquipmentType() == EquipmentType.SHIELD)
				((Shield)equipment).onUnequipped(player, this);
			player.sendToMaster(getNext());
		}
		else
		{
			player.findMatch(source).unequip(equipment.getEquipmentType());
			player.findMatch(source).showCard(equipment);
		}
	}

}
