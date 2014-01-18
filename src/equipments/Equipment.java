package equipments;

import player.PlayerOriginalClientComplete;
import update.Update;
import core.Card;
import core.Operation;
import events.EquipOperation;

/**
 * The "Equipment" type of cards, consisting of all equipments
 * @author Harry
 *
 */
public abstract class Equipment extends Card
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5968539700238484665L;
	private int equipmentType;//1.Weapon 2.Shield 3.Horse+ 4.Horse-
	private boolean equipped;
	public static final int WEAPON = 1;
	public static final int SHIELD = 2;
	public static final int HORSEPLUS = 3;
	public static final int HORSEMINUS = 4;

	public Equipment(int num, int suit,int equipmentType)
	{
		super(num, suit, Card.EQUIPMENT);
		this.equipped = false;
		this.equipmentType = equipmentType;
	}
	public void setEquipped(boolean b)
	{
		equipped = b;
	}
	public boolean isEquipped()
	{
		return equipped;
	}
	public int getEquipmentType()
	{
		return equipmentType;
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
