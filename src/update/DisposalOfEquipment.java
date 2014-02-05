package update;

import java.util.ArrayList;

import cards.Card;
import cards.equipments.Equipment;
import player.PlayerOriginalClientComplete;
import core.Framework;
import core.PlayerInfo;

public class DisposalOfEquipment implements Update
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3882089868279910147L;
	private PlayerInfo source;
	private Update next;
	private ArrayList<Card> equipments;
	public DisposalOfEquipment(PlayerInfo source,Equipment card,Update next)
	{
		this.source = source;
		this.equipments = new ArrayList<Card>();
		equipments.add(card);
		this.next = next;
	}
	public DisposalOfEquipment(PlayerInfo source,ArrayList<Equipment> equipments,Update next)
	{
		this.source = source;
		this.equipments = new ArrayList<Card>();
		for(Equipment e : equipments)
			this.equipments.add(e);
		this.next = next;
	}
	@Override
	public void frameworkOperation(Framework framework)
	{
		framework.getDeck().discardAll(equipments);
		framework.sendToAllClients(this);
	}
	@Override
	public void playerOperation(PlayerOriginalClientComplete player) 
	{
		System.out.println(player.getName()+" DisposalOfEquipment ");
		if(player.matches(source))
		{
			player.showCards(equipments);
			player.sendToMaster(next);
		}
		else
			player.findMatch(source).showCards(equipments);
	}
}
