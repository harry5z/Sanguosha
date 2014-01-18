package equipments;

import player.PlayerOriginalClientComplete;
import update.Update;
import core.Equipment;
import core.Operation;
import events.EquipOperation;




public class HorsePlus extends Equipment
{
	private String name;
	public HorsePlus(int num, int suit, String name) 
	{
		super(num, suit, Equipment.HORSEPLUS);
		this.name = name;
	}
	public int getDistance()
	{
		return 1;
	}
	@Override
	public String getName()
	{
		return name;
	}
	@Override
	public Operation onActivatedBy(PlayerOriginalClientComplete player,Update next) 
	{
		player.setCardOnHandSelected(this, true);
		player.setCancelEnabled(true);
		player.setConfirmEnabled(true);
		return new EquipOperation(player.getPlayerInfo(),this,next);
	}
	@Override
	public boolean isActivatableBy(PlayerOriginalClientComplete player) 
	{
		return true;
	}
}
