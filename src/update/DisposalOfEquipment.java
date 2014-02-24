package update;

import java.util.ArrayList;
import java.util.List;

import player.PlayerClientComplete;
import cards.Card;
import cards.equipments.Equipment;
import core.Framework;
import core.PlayerInfo;

public class DisposalOfEquipment extends Update
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3882089868279910147L;
	private PlayerInfo source;
	private ArrayList<Card> equipments;
	public DisposalOfEquipment(PlayerInfo source,Equipment card,Update next)
	{
		super(next);
		this.source = source;
		this.equipments = new ArrayList<Card>();
		equipments.add(card);
	}
	public DisposalOfEquipment(PlayerInfo source,Update next, List<Equipment> equipments)
	{
		super(next);
		this.source = source;
		this.equipments = new ArrayList<Card>();
		for(Equipment e : equipments)
			this.equipments.add(e);
	}
	@Override
	public void frameworkOperation(Framework framework)
	{
		framework.getDeck().discardAll(equipments);
		framework.sendToAllClients(this);
	}
	@Override
	public void playerOperation(PlayerClientComplete player) 
	{
		System.out.println(player.getName()+" DisposalOfEquipment ");
		if(player.matches(source))
		{
			player.showCards(equipments);
			player.sendToMaster(getNext());
		}
		else
			player.findMatch(source).showCards(equipments);
	}
}
