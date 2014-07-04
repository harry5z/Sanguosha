package core.server;

import net.server.Server;
import commands.GraphicsUpdate;
import listeners.EquipmentListener;
import player.PlayerComplete;
import cards.equipments.Equipment;
import cards.equipments.Equipment.EquipmentType;

public class EquipmentUpdater extends GraphicsUpdater implements EquipmentListener
{

	public EquipmentUpdater(Server server, PlayerComplete player)
	{
		super(server, player);
	}

	@Override
	public void onEquipped(final Equipment equipment) 
	{
		server.sendToAllClients(new GraphicsUpdate() 
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 3594225045305536409L;

			@Override
			public void ClientOperation(PlayerComplete player) 
			{
				player.findMatch(info).equip(equipment);
			}
		});
	}

	@Override
	public void onUnequipped(final EquipmentType type) 
	{
		server.sendToAllClients(new GraphicsUpdate() 
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 51957951760360724L;

			@Override
			public void ClientOperation(PlayerComplete player) 
			{
				player.findMatch(info).unequip(type);
			}
		});
	}

}
