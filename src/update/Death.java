package update;

import java.util.ArrayList;

import player.PlayerOriginalClientComplete;
import core.Framework;
import core.PlayerInfo;
import equipments.Equipment;

/**
 * Death update
 * @author Harry
 *
 */
public class Death implements Update
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4321634819282332287L;
	private PlayerInfo victim;
	private Update next;
	public Death(PlayerInfo victim,Update next)
	{
		this.victim = victim;
		this.next = next;
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
				equipmentsToDispose.add(player.unequip(Equipment.WEAPON));
			if(player.isEquippedShield())
				equipmentsToDispose.add(player.unequip(Equipment.SHIELD));
			if(player.isEquippedHorsePlus())
				equipmentsToDispose.add(player.unequip(Equipment.HORSEPLUS));
			if(player.isEquippedHorseMinus())
				equipmentsToDispose.add(player.unequip(Equipment.HORSEMINUS));
			//here for decision area
			//here for player skill cards
			player.sendToMaster(new DisposalOfCards(victim,player.getCardsOnHand(),new DisposalOfEquipment(victim,equipmentsToDispose,next)));//victim discards all cards
		}
		else
			player.findMatch(victim).kill();
	}

}
