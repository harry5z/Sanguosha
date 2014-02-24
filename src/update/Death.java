package update;

import java.util.ArrayList;
import java.util.List;

import player.PlayerClientComplete;
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
	public void playerOperation(PlayerClientComplete player)
	{
		System.out.println(player.getName()+" Death ");
		if(player.matches(victim))
		{
			player.kill();//kill victim
			List<Equipment> equipmentsToDispose = new ArrayList<Equipment>();
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
			player.sendToMaster(new DisposalOfCards(victim,player.getCardsOnHand(),new DisposalOfEquipment(victim,getNext(),equipmentsToDispose)));//victim discards all cards
		}
		else
			player.findMatch(victim).kill();
	}

}
