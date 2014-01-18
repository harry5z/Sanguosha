package update;

import player.PlayerOriginalClientComplete;
import core.Card;
import core.Equipment;
import core.Framework;
import core.PlayerInfo;

public class DisposalOfEquipment implements Update
{
	private PlayerInfo source;
	private Card card;
	private Update next;
	
	public DisposalOfEquipment(PlayerInfo source,Card card,Update next)
	{
		this.source = source;
		this.card = card;
		this.next = next;
	}
	@Override
	public void frameworkOperation(Framework framework)
	{
		framework.getDeck().discard(card);
		framework.sendToAllClients(this);
	}
	@Override
	public void playerOperation(PlayerOriginalClientComplete player) 
	{
		System.out.println(player.getName()+" DisposalOfEquipment ");
		if(player.isEqualTo(source))
		{
			player.showCard(card);;
			player.sendToMaster(next);
		}
		else
			player.findMatch(source).showCard(card);;
	}
}
