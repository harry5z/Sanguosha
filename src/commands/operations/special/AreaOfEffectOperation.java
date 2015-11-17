package commands.operations.special;

import java.util.ArrayList;
import java.util.List;

import player.PlayerComplete;
import player.PlayerOriginal;
import cards.Card;
import cards.equipments.Equipment.EquipmentType;

import commands.Command;
import commands.Damage;
import commands.game.server.UseOfCardsInGameServerCommand;
import core.PlayerInfo;

public abstract class AreaOfEffectOperation extends SpecialOperation
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4879416749526010430L;
	protected PlayerInfo source;
	private List<PlayerInfo> visitedPlayers;
	protected PlayerInfo currentTarget;
	protected Card aoe;
	protected boolean sent;
	public AreaOfEffectOperation(PlayerComplete player, Card aoe, Command next) 
	{
		super(next, player.getCurrentStage().getSource());
		this.aoe = aoe;
		this.source = player.getPlayerInfo();
		this.currentTarget = player.getNextPlayerAlive();
		visitedPlayers = new ArrayList<PlayerInfo>();
		sent = false;
	}
	@Override
	public PlayerInfo getCurrentTarget()
	{
		return currentTarget;
	}
	@Override
	public void onPlayerSelected(PlayerComplete operator,PlayerOriginal player)
	{
		// no target selection
	}

	@Override
	public void onCardSelected(PlayerComplete operator, Card card) 
	{
		cardSelectedAsReaction(operator, card);
	}

	@Override
	public void onCancelledBy(PlayerComplete player) 
	{
		if(player.equals(source))//cancel operation
		{
			this.cancelOperation(player, aoe);
		}
		else//target
		{
			if(reactionCard != null)//cancel selection
			{
				player.getGameListener().setCardSelected(reactionCard, false);
				player.getGameListener().setConfirmEnabled(false);
				reactionCard = null;
				player.setOperation(this);
			}
			else //give up
			{
				setStage(AFTER);
				player.setAllCardsOnHandSelectable(false);
				player.getGameListener().setCancelEnabled(false);
				player.setOperation(null);
				player.sendToServer(new Damage(aoe,source,currentTarget,this));
			}
		}
	}

	@Override
	public void onConfirmedBy(PlayerComplete player) 
	{
		if(!sent)//confirm AOE
		{
			sent = true;
			player.getGameListener().setCardSelected(aoe, false);
			player.sendToServer(new UseOfCardsInGameServerCommand(source,aoe,this));
		}
		else//target reacted
		{
			player.getGameListener().setCardSelected(reactionCard, false);
			setStage(AFTER);
			player.sendToServer(new UseOfCardsInGameServerCommand(currentTarget,reactionCard,this));
		}
	}
	@Override
	protected void playerOpBefore(PlayerComplete player)
	{
		if(player.equals(currentTarget))
		{
			//here for future skills
			if(player.isEquipped(EquipmentType.SHIELD) && !player.getShield().mustReactTo(aoe))
				setStage(AFTER);
			else
				setStage(NEUTRALIZATION);
			player.sendToServer(this);
		}
	}
	@Override
	protected void playerOpEffect(PlayerComplete player) 
	{
		if(player.equals(currentTarget))
		{
			reactionCard = null;
			if(!player.isAlive())//already dead
			{
				playerOpAfter(player);//next player
				return;
			}
			player.setOperation(this);
			AOETargetOperation(player);
		}
		
	}
	/**
	 * what does a target do, when a special card takes effect
	 * @param target : current target of special card
	 */
	protected abstract void AOETargetOperation(PlayerComplete target);
	@Override
	protected void playerOpAfter(PlayerComplete player)
	{
		if(player.equals(currentTarget))
		{
			visitedPlayers.add(currentTarget);
			currentTarget = player.getNextPlayerAlive();
			setStage(BEFORE);
			
			if(currentTarget.equals(source) || visitedPlayers.contains(currentTarget))//cycle complete
				player.sendToServer(getNext());
			else
				player.sendToServer(this);
		}
	}
}
