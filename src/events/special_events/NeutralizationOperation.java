package events.special_events;

import java.util.ArrayList;

import player.PlayerOriginal;
import player.PlayerOriginalClientComplete;
import specials.Neutralization;
import update.UseOfCards;
import core.Card;
import core.Framework;
import core.Operation;
import core.PlayerInfo;
import core.Update;

public class NeutralizationOperation implements Operation
{
	private SpecialOperation next;
	private int alivePlayerCount;
	private ArrayList<PlayerInfo> cancelledPlayers;
	private PlayerInfo turnPlayer;
	private boolean neutralized;
	private Card neutralization;
	
	public NeutralizationOperation(SpecialOperation next,PlayerInfo turnPlayer)
	{
		this.next = next;
		this.turnPlayer = turnPlayer;
		this.neutralized = false;
		neutralization = null;
		cancelledPlayers = new ArrayList<PlayerInfo>();
	}
	@Override
	public void frameworkOperation(Framework framework)
	{
		framework.sendToAllClients(this);
	}

	@Override
	public void playerOperation(PlayerOriginalClientComplete player)
	{
		System.out.println(player.getName()+" NeutralizationOperation ");
		alivePlayerCount = player.getNumberOfPlayersAlive();
		if(cancelledPlayers.size() == alivePlayerCount)
		{
			player.setOperation(null);
			if(player.isEqualTo(turnPlayer))
			{
				if(neutralized)//ineffective
					player.sendToMaster(next.getNext());
				else//effective
				{
					next.nextStage();
					player.sendToMaster(next);
				}
			}
			return;
		}
		
		player.setOperation(this);//bug, previous selection?
		if(!cancelledPlayers.contains(player.getPlayerInfo()))
		{
			player.setCardSelectableByName(Neutralization.NEUTRALIZATION, true);
			player.setCancelEnabled(true);
		}
	}

	@Override
	public void onPlayerSelected(PlayerOriginalClientComplete operator,PlayerOriginal player) 
	{
		//no player selection
	}

	@Override
	public void onCardSelected(PlayerOriginalClientComplete operator, Card card)
	{
		if(neutralization == null)//new card
		{
			neutralization = card;
			operator.setCardOnHandSelected(card, true);
			operator.setConfirmEnabled(true);
		}
		else
		{
			operator.setCardOnHandSelected(neutralization, false);
			if(neutralization.equals(card))
			{
				neutralization = null;
				operator.setConfirmEnabled(false);
			}
			else
			{
				neutralization = card;
				operator.setCardOnHandSelected(card, true);
			}
		}
	}

	@Override
	public void onCancelledBy(PlayerOriginalClientComplete player) 
	{
		if(neutralization == null)
		{
			player.setAllCardsOnHandSelectable(false);
			player.setCancelEnabled(false);
			player.setOperation(null);
			cancelledPlayers.add(player.getPlayerInfo());
			player.sendToMaster(this);
		}
		else
		{
			player.setCardOnHandSelected(neutralization, false);
			neutralization = null;
			player.setConfirmEnabled(false);
			player.setOperation(this);
		}
	}

	@Override
	public void onConfirmedBy(PlayerOriginalClientComplete player)
	{
		cancelledPlayers.clear();;
		neutralized = !neutralized;
		player.setAllCardsOnHandSelectable(false);
		player.setOperation(null);
		Card temp = neutralization;
		neutralization = null;
		player.sendToMaster(new UseOfCards(player.getPlayerInfo(),temp,this));
	}

}
