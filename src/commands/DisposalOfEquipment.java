package commands;

import java.util.ArrayList;
import java.util.List;

import player.PlayerComplete;
import cards.Card;
import cards.equipments.Equipment;
import core.Game;
import core.PlayerInfo;

public class DisposalOfEquipment extends Command
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3882089868279910147L;
	private PlayerInfo source;
	private ArrayList<Card> equipments;
	public DisposalOfEquipment(PlayerInfo source,Equipment card,Command next)
	{
		super(next);
		this.source = source;
		this.equipments = new ArrayList<Card>();
		equipments.add(card);
	}
	public DisposalOfEquipment(PlayerInfo source,Command next, List<Equipment> equipments)
	{
		super(next);
		this.source = source;
		this.equipments = new ArrayList<Card>();
		for(Equipment e : equipments)
			this.equipments.add(e);
	}
	@Override
	public void ServerOperation(Game framework)
	{
		framework.getDeck().discardAll(equipments);
		framework.sendToAllClients(this);
	}
	@Override
	public void ClientOperation(PlayerComplete player) 
	{
		System.out.println(player.getName()+" DisposalOfEquipment ");
		if(player.equals(source))
		{
			player.showCards(equipments);
			player.sendToServer(getNext());
		}
		else
			player.findMatch(source).showCards(equipments);
	}
}
