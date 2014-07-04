package commands.operations;

import commands.Command;
import commands.LossOfEquipment;
import player.PlayerComplete;
import player.PlayerOriginal;
import cards.Card;
import cards.equipments.Equipment;
import core.Game;
import core.PlayerInfo;

public class EquipOperation extends Operation
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4444649804902222892L;
	private Equipment equipment;
	private PlayerInfo source;
	
	public EquipOperation(PlayerInfo source, Equipment equipment, Command next)
	{
		super(next);
		this.equipment = equipment;
		this.source = source;
	}
	@Override
	public void ServerOperation(Game framework) 
	{
		framework.sendToAllClients(this);
	}

	@Override
	public void ClientOperation(PlayerComplete player)
	{
		if(player.equals(source))
		{
			player.equip(equipment);
			player.removeCardFromHand(equipment);
			if(old != null)
			{
				player.sendToServer(new LossOfEquipment(source,getNext(),old));
			}
			else
				player.sendToServer(getNext());
		}
		else
		{
			player.findMatch(source).removeCardFromHand(equipment);
			player.findMatch(source).equip(equipment);
		}
	}

	@Override
	public void onPlayerSelected(PlayerComplete operator,PlayerOriginal player) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCardSelected(PlayerComplete operator, Card card) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCancelledBy(PlayerComplete player) 
	{
		player.setConfirmEnabled(false);
		player.setCancelEnabled(false);
		player.setCardOnHandSelected(equipment, false);
	}

	@Override
	public void onConfirmedBy(PlayerComplete player)
	{
		player.setOperation(null);
		player.setCardOnHandSelected(equipment, false);
		player.setCancelEnabled(false);
		player.sendToServer(this);
	}

}
