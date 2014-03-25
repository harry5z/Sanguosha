package update.operations.special_operations;

import cards.Card;
import player.PlayerOriginal;
import player.PlayerClientComplete;
import update.Damage;
import update.Damage.Element;
import update.DisposalOfCards;
import update.Update;
import update.UseOfCards;
import core.PlayerInfo;

public class FireAttackOperation extends SpecialOperation
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3276086757026451171L;
	private PlayerInfo source;
	private PlayerInfo target;
	private boolean shown;
	private boolean sent;
	private Card fireAttack;
	
	public FireAttackOperation(PlayerClientComplete player, Card fireAttack,Update next)
	{
		super(next,player.getCurrentStage().getSource());
		this.source = player.getPlayerInfo();
		this.target = null;
		this.fireAttack = fireAttack;
		shown = false;
		sent = false;
	}

	@Override
	public void onPlayerSelected(PlayerClientComplete operator,PlayerOriginal player)
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
		cardSelectedAsReaction(operator, card);
	}

	@Override
	public void onCancelledBy(PlayerClientComplete player) 
	{
		if(!shown)//cancel fireattack
		{
			player.setCardOnHandSelected(fireAttack, false);//unselect duel
			player.setConfirmEnabled(false);//cannot confirm
			if(target != null)//unselect target
				player.unselectTarget(target);
			player.setAllTargetsSelectableIncludingSelf(false);
			player.setCancelEnabled(false);
			player.setOperation(null);
		}
		else//cancel damage confirmation
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
				player.sendToMaster(getNext());
			}
		}
	}

	@Override
	public void onConfirmedBy(PlayerClientComplete player) 
	{
		player.setOperation(null);
		if(sent && !shown)//confirm showing card
		{
			player.setCardOnHandSelected(reactionCard, false);
			player.setAllCardsOnHandSelectable(false);
			shown = true;
			player.sendToMaster(this);
		}
		else if(!sent && !shown)//confirm fire attack
		{
			sent = true;
			player.unselectTarget(target);
			player.setCardOnHandSelected(fireAttack, false);
			player.setAllTargetsSelectableIncludingSelf(false);
			player.sendToMaster(new UseOfCards(player.getPlayerInfo(),fireAttack,this));
		}
		else if(sent && shown)//confirm damage
		{
			player.setCardOnHandSelected(reactionCard, false);
			player.sendToMaster(new DisposalOfCards(source,reactionCard,new Damage(1,Element.FIRE,source,target,getNext())));
		}
	}
	@Override
	protected void playerOpEffect(PlayerClientComplete player) 
	{
		if(shown)
		{
			player.findMatch(target).showCard(reactionCard);
		}
		if(!shown && player.matches(target))//showing card
		{
			if(player.getCardsOnHandCount() == 0)//cannot be attacked
			{
				player.sendToMaster(getNext());//continue
				return;
			}
			player.setAllCardsOnHandSelectable(true);
			player.setOperation(this);
			player.getGameListener().onSetMessage("You are being Fire Attacked, please show a card on hand");
		}
		else if(shown && player.matches(source))//choosing card to dispose
		{
			for(Card c : player.getCardsOnHand())
				if(c.getSuit() == reactionCard.getSuit())
					player.setCardSelectable(c, true);
			reactionCard = null;
			player.setCancelEnabled(true);
			player.setOperation(this);
			player.getGameListener().onSetMessage("Choose a card with the same suit to cause damage");
		}		
	}

	@Override
	public String getName() 
	{
		return "Fire Attack";
	}

	@Override
	public PlayerInfo getCurrentTarget() 
	{
		return target;
	}
}
