package events.special_events;

import basics.Attack;
import player.PlayerOriginal;
import player.PlayerOriginalClientComplete;
import update.Damage;
import update.Update;
import update.UseOfCards;
import core.Card;
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
//	private Card attack;
	
	public DuelOperation(PlayerInfo source,PlayerInfo turnPlayer,Card duel,Update next)
	{
		super(next,turnPlayer);
		this.duel = duel;
		this.source = source;
		this.target = null;
//		this.attack = null;
	}
	
	@Override
	public void onPlayerSelected(PlayerOriginalClientComplete operator, PlayerOriginal player) 
	{
		if(target == null)//select target
		{
			target = player.getPlayerInfo();
			operator.selectTarget(target);//target selected
			operator.setConfirmEnabled(true);//can confirm
		}
		else
		{
			if(player.isEqualTo(target))//cancel
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
	public void onCardSelected(PlayerOriginalClientComplete operator, Card card)
	{
		this.cardSelectedAsReaction(operator, card);
//		if(attack != null)//unselect previous
//		{
//			operator.setCardOnHandSelected(attack, false);
//			if(attack.equals(card))//unselect
//			{
//				attack = null;
//				operator.setConfirmEnabled(false);
//			}
//			else//change
//			{
//				attack = card;
//				operator.setCardOnHandSelected(card, true);
//			}
//		}
//		else //select new
//		{
//			attack = card;
//			operator.setCardOnHandSelected(card, true);
//			operator.setConfirmEnabled(true);
//		}
		
	}

	@Override
	public void onCancelledBy(PlayerOriginalClientComplete player) 
	{
		if(player.isEqualTo(source))//not sent yet
		{
			player.setCardOnHandSelected(duel, false);//unselect duel
			player.setConfirmEnabled(false);//cannot confirm
			if(target != null)//unselect target
				player.unselectTarget(target);
			player.setAllTargetsSelectableExcludingSelf(false);
			player.setCancelEnabled(false);
			player.setOperation(null);
		}
		else if(player.isEqualTo(target))
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
	public void onConfirmedBy(PlayerOriginalClientComplete player)
	{
		if(player.isEqualTo(source))//start duel
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
	protected void playerOpEffect(PlayerOriginalClientComplete player) 
	{
		if(player.isEqualTo(target))
		{
			System.out.println(player.getName()+" DuelOperation ");
			player.setCardSelectableByName(Attack.ATTACK, true);
			player.setCancelEnabled(true);
			player.setOperation(this);
		}
		
	}

	
}
