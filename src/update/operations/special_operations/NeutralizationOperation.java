package update.operations.special_operations;

import java.util.ArrayList;
import java.util.List;

import player.PlayerClientComplete;
import player.PlayerOriginal;
import update.UseOfCards;
import update.operations.Operation;
import cards.Card;
import cards.specials.instant.Neutralization;
import core.Framework;
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
	public void frameworkOperation(Framework framework)
	{
		framework.sendToAllClients(this);
	}

	@Override
	public void playerOperation(PlayerClientComplete player)
	{
		if(cancelledPlayers.size() == player.getNumberOfPlayersAlive())
		{
			player.setOperation(null);
			if(player.matches(turnPlayer))
			{
				if(neutralized)//ineffective
				{
					next.setStage(SpecialOperation.AFTER);
					player.sendToMaster(next);
				}
				else//effective
				{
					next.nextStage();
					player.sendToMaster(next);
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
	public void onPlayerSelected(PlayerClientComplete operator,PlayerOriginal player) 
	{
		//no player selection
	}

	@Override
	public void onCardSelected(PlayerClientComplete operator, Card card)
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
	public void onCancelledBy(PlayerClientComplete player) 
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
	public void onConfirmedBy(PlayerClientComplete player)
	{
		cancelledPlayers.clear();
		neutralized = !neutralized;
		player.setAllCardsOnHandSelectable(false);
		Card temp = neutralization;
		neutralization = null;
		player.sendToMaster(new UseOfCards(player.getPlayerInfo(),temp,this));
	}

}
