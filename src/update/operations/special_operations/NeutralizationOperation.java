package update.operations.special_operations;

import java.util.ArrayList;

import cards.Card;
import cards.specials.instant.Neutralization;
import player.PlayerOriginal;
import player.PlayerOriginalClientComplete;
import update.UseOfCards;
import update.operations.Operation;
import core.Framework;
import core.PlayerInfo;

public class NeutralizationOperation implements Operation
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5444652755918048029L;
	private SpecialOperation next;
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
			player.getGameListener().onSetMessage(next.getCurrentPlayer().getName()+" is the target of "+next.getName()+", do you use Neutralization?");
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
		cancelledPlayers.clear();
		neutralized = !neutralized;
		player.setAllCardsOnHandSelectable(false);
		player.setOperation(null);
		Card temp = neutralization;
		neutralization = null;
		player.sendToMaster(new UseOfCards(player.getPlayerInfo(),temp,this));
	}

}
