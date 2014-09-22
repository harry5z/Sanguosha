package commands.operations.special;

import java.util.ArrayList;
import java.util.List;

import commands.UseOfCards;
import commands.operations.Operation;
import player.PlayerComplete;
import player.PlayerOriginal;
import cards.Card;
import cards.specials.instant.Neutralization;
import core.Game;
import core.PlayerInfo;

public class NeutralizationOperation extends Operation
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5444652755918048029L;
	private SpecialOperation next;
	private List<PlayerInfo> cancelledPlayers;
	private PlayerInfo turnPlayer;
	private boolean neutralized;
	private Card neutralization;
	
	public NeutralizationOperation(SpecialOperation next,PlayerInfo turnPlayer)
	{
		super(next);
		this.next = next;
		this.turnPlayer = turnPlayer;
		this.neutralized = false;
		neutralization = null;
		cancelledPlayers = new ArrayList<PlayerInfo>();
	}
	@Override
	public void ServerOperation(Game framework)
	{
		framework.sendToAllClients(this);
	}

	@Override
	public void ClientOperation(PlayerComplete player)
	{
		if(cancelledPlayers.size() == player.getNumberOfPlayersAlive())
		{
			player.setOperation(null);
			if(player.equals(turnPlayer))
			{
				if(neutralized)//ineffective
				{
					next.setStage(SpecialOperation.AFTER);
					player.sendToServer(next);
				}
				else//effective
				{
					next.nextStage();
					player.sendToServer(next);
				}
			}
			return;
		}
		
		player.setOperation(this);//bug, previous selection?
		if(player.isAlive() && !cancelledPlayers.contains(player.getPlayerInfo()))
		{
			player.setCardSelectableByName(Neutralization.NEUTRALIZATION, true);
			player.getGameListener().setMessage(next.getCurrentTarget().getName()+" is the target of "+next.getName()+", do you use Neutralization?");
			player.setCancelEnabled(true);
		}
	}

	@Override
	public void onPlayerSelected(PlayerComplete operator,PlayerOriginal player) 
	{
		//no player selection
	}

	@Override
	public void onCardSelected(PlayerComplete operator, Card card)
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
	public void onCancelledBy(PlayerComplete player) 
	{
		if(neutralization == null)
		{
			player.setAllCardsOnHandSelectable(false);
			player.setCancelEnabled(false);
			player.setOperation(null);
			cancelledPlayers.add(player.getPlayerInfo());
			player.sendToServer(this);
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
	public void onConfirmedBy(PlayerComplete player)
	{
		cancelledPlayers.clear();
		neutralized = !neutralized;
		player.setAllCardsOnHandSelectable(false);
		Card temp = neutralization;
		neutralization = null;
		player.sendToServer(new UseOfCards(player.getPlayerInfo(),temp,this));
	}

}
