package commands;

import player.PlayerComplete;
import cards.equipments.Equipment;
import cards.equipments.Equipment.EquipmentType;
import cards.equipments.shields.Shield;
import core.Game;
import core.PlayerInfo;

public class Unequip extends Command
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5339794175888880779L;
	private PlayerInfo source;
	private Equipment equipment;
	private boolean disposed;
	
	public Unequip(PlayerInfo source, Command next, Equipment equipment, boolean disposed) 
	{
		super(next);
		this.source = source;
		this.equipment = equipment;
		this.disposed = disposed;
	}

	@Override
	public void ServerOperation(Game framework) 
	{
		if(disposed)
			framework.getDeck().discard(equipment);
		framework.sendToAllClients(this);		
	}

	@Override
	public void ClientOperation(PlayerComplete player)
	{
		if(player.equals(source))
		{
			player.unequip(equipment.getEquipmentType());
			if(equipment.getEquipmentType() == EquipmentType.SHIELD)
				((Shield)equipment).onUnequipped(player, this);
			player.sendToServer(getNext());
		}
		else
		{
			player.findMatch(source).unequip(equipment.getEquipmentType());
		}
		if(disposed)
			player.findMatch(source).showCard(equipment);
	}

}
