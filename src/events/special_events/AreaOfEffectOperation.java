package events.special_events;

import java.util.Stack;

import player.PlayerOriginal;
import player.PlayerOriginalClientComplete;
import update.Damage;
import update.Update;
import update.UseOfCards;
import core.Card;
import core.PlayerInfo;

public abstract class AreaOfEffectOperation extends SpecialOperation
{
	private PlayerInfo source;
	private Stack<PlayerInfo> targets;
	private PlayerInfo currentTarget;
	private Card aoe;
	public AreaOfEffectOperation(PlayerOriginalClientComplete player, Card aoe, Update next) 
	{
		super(next, player.getCurrentStage().getSource());
		this.aoe = aoe;
		this.source = player.getPlayerInfo();
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
				this.nextStage();
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
		if(player.isEqualTo(source))//confirm AOE
		{
			player.setCardOnHandSelected(aoe, false);
			player.sendToMaster(new UseOfCards(source,aoe,this));
		}
		else//target reacted
		{
			player.setCardOnHandSelected(reactionCard, false);
			continueOperation(player);//next target
			player.sendToMaster(new UseOfCards(currentTarget,reactionCard,this));
		}
	}

	@Override
	protected void playerOpEffect(PlayerOriginalClientComplete player) 
	{
		if(player.isEqualTo(currentTarget))
		{
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
		continueOperation(player);
		player.sendToMaster(this);
	}
	private void continueOperation(PlayerOriginalClientComplete player)
	{
		if(targets.isEmpty())//cycle complete
		{
			super.playerOpAfter(player);
			return;
		}
		currentTarget = targets.pop();
		setStage(BEFORE);
	}
}
