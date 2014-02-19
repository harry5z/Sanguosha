package update.operations;

import cards.Card;
import cards.equipments.Equipment;
import player.PlayerOriginal;
import player.PlayerClientComplete;
import update.DisposalOfEquipment;
import update.Update;
import core.Framework;
import core.PlayerInfo;

public class EquipOperation extends Operation
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4444649804902222892L;
	private Equipment equipment;
	private PlayerInfo source;
	
	public EquipOperation(PlayerInfo source, Equipment equipment, Update next)
	{
		super(next);
		this.equipment = equipment;
		this.source = source;
	}
	@Override
	public void frameworkOperation(Framework framework) 
	{
		framework.sendToAllClients(this);
	}

	@Override
	public void playerOperation(PlayerClientComplete player)
	{
		if(player.matches(source))
		{
			Equipment old = player.equip(equipment);
			if(old != null)
			{
				player.sendToMaster(new DisposalOfEquipment(source,old,getNext()));
			}
			else
				player.sendToMaster(getNext());
		}
		else
			player.findMatch(source).equip(equipment);
	}

	@Override
	public void onPlayerSelected(PlayerClientComplete operator,PlayerOriginal player) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCardSelected(PlayerClientComplete operator, Card card) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCancelledBy(PlayerClientComplete player) 
	{
		player.setConfirmEnabled(false);
		player.setCancelEnabled(false);
		player.setCardOnHandSelected(equipment, false);
	}

	@Override
	public void onConfirmedBy(PlayerClientComplete player)
	{
		player.setOperation(null);
		player.setCardOnHandSelected(equipment, false);
		player.setCancelEnabled(false);
		player.sendToMaster(this);
	}

}
