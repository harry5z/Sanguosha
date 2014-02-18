package update;

import java.util.ArrayList;

import player.PlayerOriginalClientComplete;
import cards.equipments.Equipment;
import cards.equipments.Equipment.EquipmentType;
import core.Framework;
import core.PlayerInfo;

/**
 * Death update
 * @author Harry
 *
 */
public class Death extends Update
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4321634819282332287L;
	private PlayerInfo victim;
	public Death(PlayerInfo victim,Update next)
	{
		super(next);
		this.victim = victim;
	}
	@Override
	public void frameworkOperation(Framework framework) 
	{
		framework.sendToAllClients(this);
	}

	@Override
	public void playerOperation(PlayerOriginalClientComplete player)
	{
		System.out.println(player.getName()+" Death ");
		if(player.matches(victim))
		{
			player.kill();//kill victim
			ArrayList<Equipment> equipmentsToDispose = new ArrayList<Equipment>();
			if(player.isEquippedWeapon())
				equipmentsToDispose.add(player.unequip(EquipmentType.WEAPON));
			if(player.isEquippedShield())
				equipmentsToDispose.add(player.unequip(EquipmentType.SHIELD));
			if(player.isEquippedHorsePlus())
				equipmentsToDispose.add(player.unequip(EquipmentType.HORSEPLUS));
			if(player.isEquippedHorseMinus())
				equipmentsToDispose.add(player.unequip(EquipmentType.HORSEMINUS));
			//here for decision area
			//here for player skill cards
			player.sendToMaster(new DisposalOfCards(victim,player.getCardsOnHand(),new DisposalOfEquipment(victim,equipmentsToDispose,getNext())));//victim discards all cards
		}
		else
			player.findMatch(victim).kill();
	}

}
