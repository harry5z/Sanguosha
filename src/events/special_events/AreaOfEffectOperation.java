package events.special_events;

import java.util.ArrayList;
import player.PlayerOriginal;
import player.PlayerOriginalClientComplete;
import update.Damage;
import update.Update;
import update.UseOfCards;
import core.Card;
import core.PlayerInfo;

public abstract class AreaOfEffectOperation extends SpecialOperation
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4879416749526010430L;
	protected PlayerInfo source;
	private ArrayList<PlayerInfo> visitedPlayers;
	protected PlayerInfo currentTarget;
	private Card aoe;
	private boolean sent;
	public AreaOfEffectOperation(PlayerOriginalClientComplete player, Card aoe, Update next) 
	{
		super(next, player.getCurrentStage().getSource());
		this.aoe = aoe;
		this.source = player.getPlayerInfo();
		this.currentTarget = player.getNextPlayerAlive();
		visitedPlayers = new ArrayList<PlayerInfo>();
		sent = false;
	}

	@Override
	public void onPlayerSelected(PlayerOriginalClientComplete operator,PlayerOriginal player)
	{
		// no target selection
	}

	@Override
	public void onCardSelected(PlayerOriginalClientComplete operator, Card card) 
	{
		cardSelectedAsReaction(operator, card);
	}

	@Override
	public void onCancelledBy(PlayerOriginalClientComplete player) 
	{
		if(player.isEqualTo(source))//cancel operation
		{
			player.setCardOnHandSelected(aoe, false);
			player.setConfirmEnabled(false);
			player.setCancelEnabled(false);
			player.setOperation(null);
		}
		else//target
		{
			if(reactionCard != null)//cancel selection
			{
				player.setCardOnHandSelected(reactionCard, false);
				player.setConfirmEnabled(false);
				reactionCard = null;
				player.setOperation(this);
			}
			else //give up
			{
				setStage(AFTER);
				player.setAllCardsOnHandSelectable(false);
				player.setCancelEnabled(false);
				player.setOperation(null);
				player.sendToMaster(new Damage(aoe,source,currentTarget,this));
			}
		}
	}

	@Override
	public void onConfirmedBy(PlayerOriginalClientComplete player) 
	{
		if(!sent)//confirm AOE
		{
			sent = true;
			player.setCardOnHandSelected(aoe, false);
			player.sendToMaster(new UseOfCards(source,aoe,this));
		}
		else//target reacted
		{
			player.setCardOnHandSelected(reactionCard, false);
			setStage(AFTER);
			player.sendToMaster(new UseOfCards(currentTarget,reactionCard,this));
		}
	}
	@Override
	protected void playerOpBefore(PlayerOriginalClientComplete player)
	{
		if(player.isEqualTo(currentTarget))
		{
			//here for future skills
			if(player.isEquippedShield() && !player.getShield().isRequiredToReactTo(aoe))
				setStage(AFTER);
			else
				setStage(NEUTRALIZATION);
			player.sendToMaster(this);
		}
	}
	@Override
	protected void playerOpEffect(PlayerOriginalClientComplete player) 
	{
		if(player.isEqualTo(currentTarget))
		{
			reactionCard = null;
			if(!player.isAlive())//already dead
			{
				playerOpAfter(player);//next player
				return;
			}
			System.out.println(player.getName() + "AOE");
			targetOp(player);
		}
		
	}
	/**
	 * what does a target do?
	 * @param target
	 */
	protected abstract void targetOp(PlayerOriginalClientComplete target);
	@Override
	protected void playerOpAfter(PlayerOriginalClientComplete player)
	{
		if(player.isEqualTo(currentTarget))
		{
			visitedPlayers.add(currentTarget);
			currentTarget = player.getNextPlayerAlive();
			setStage(BEFORE);
			
			if(currentTarget.equals(source) || visitedPlayers.contains(currentTarget))//cycle complete
				player.sendToMaster(getNext());
			else
				player.sendToMaster(this);
		}
	}
}
