package update.operations.special_operations;

import cards.Card;
import cards.basics.Attack;
import player.PlayerOriginal;
import player.PlayerClientComplete;
import update.Damage;
import update.Update;
import update.UseOfCards;
import core.PlayerInfo;

public class DuelOperation extends SpecialOperation
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5234628931021212612L;
	private PlayerInfo source;
	private PlayerInfo target;
	private Card duel;
	
	public DuelOperation(PlayerInfo source,PlayerInfo turnPlayer,Card duel,Update next)
	{
		super(next,turnPlayer);
		this.duel = duel;
		this.source = source;
		this.target = null;
	}
	
	@Override
	public void onPlayerSelected(PlayerClientComplete operator, PlayerOriginal player) 
	{
		if(target == null)//select target
		{
			target = player.getPlayerInfo();
			operator.selectTarget(target);//target selected
			operator.setConfirmEnabled(true);//can confirm
		}
		else
		{
			if(player.matches(target))//cancel
			{
				operator.unselectTarget(target);//no target
				operator.setConfirmEnabled(false);//cannot confirm
				target = null;
			}
			else//change
			{
				operator.unselectTarget(target);
				target = player.getPlayerInfo();
				operator.selectTarget(target);
			}
		}
	}

	@Override
	public void onCardSelected(PlayerClientComplete operator, Card card)
	{
		this.cardSelectedAsReaction(operator, card);
	}

	@Override
	public void onCancelledBy(PlayerClientComplete player) 
	{
		if(player.matches(source))//not sent yet
		{
			player.setCardOnHandSelected(duel, false);//unselect duel
			player.setConfirmEnabled(false);//cannot confirm
			if(target != null)//unselect target
				player.unselectTarget(target);
			player.setAllTargetsSelectableExcludingSelf(false);
			player.setCancelEnabled(false);
			player.setOperation(null);
		}
		else if(player.matches(target))
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
				//player.setOperation(null);
				player.sendToMaster(new Damage(duel,source,target,getNext()));
			}
		}
	}

	@Override
	public void onConfirmedBy(PlayerClientComplete player)
	{
		if(player.matches(source))//start duel
		{
			player.unselectTarget(target);
			player.setCardOnHandSelected(duel, false);
			player.setOperation(null);
			player.sendToMaster(new UseOfCards(source,duel,this));
		}
		else//target reacted
		{
			player.setCardOnHandSelected(reactionCard,false);
			//switch source and target
			PlayerInfo temp = target;
			target = source;
			source = temp;
			Card c = reactionCard;
			reactionCard = null;
			player.sendToMaster(new UseOfCards(player.getPlayerInfo(),c,this));
		}
	}

	@Override
	protected void playerOpEffect(PlayerClientComplete player) 
	{
		if(player.matches(target))
		{
			System.out.println(player.getName()+" DuelOperation ");
			player.getGameListener().setMessage("You are being dueled, please use an Attack");
			player.setCardSelectableByName(Attack.ATTACK, true);
			player.setCardSelectableByName(Attack.FIRE_ATTACK, true);
			player.setCardSelectableByName(Attack.THUNDER_ATTACK, true);
			player.setCancelEnabled(true);
			player.setOperation(this);
		}
		
	}

	@Override
	public String getName() 
	{
		return "Duel";
	}

	@Override
	public PlayerInfo getCurrentTarget() 
	{
		return target;
	}
}
