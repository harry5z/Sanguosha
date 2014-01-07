package events.special_events;

import basics.Attack;
import player.PlayerOriginal;
import player.PlayerOriginalClientComplete;
import update.Damage;
import update.DisposalOfCards;
import update.UseOfCards;
import core.Card;
import core.Framework;
import core.Operation;
import core.PlayerInfo;
import core.Update;

public class FireAttackOperation extends SpecialOperation
{
	private PlayerInfo source;
	private PlayerInfo target;
	private boolean shown;
	private boolean sent;
	private Card fireAttack;
	private Card cardShown;
	private Card cardDisposed;
	
	public FireAttackOperation(PlayerOriginalClientComplete player, Card fireAttack,Update next)
	{
		super(next,player.getCurrentStage().getSource());
		this.source = player.getPlayerInfo();
		this.target = null;
		this.fireAttack = fireAttack;
		shown = false;
		sent = false;
		cardShown = null;
		cardDisposed = null;
	}

	@Override
	public void onPlayerSelected(PlayerOriginalClientComplete operator,PlayerOriginal player)
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
		if(!shown)//target selecting card to show
		{
			if(cardShown != null)//unselect previous
			{
				operator.setCardOnHandSelected(cardShown, false);
				if(cardShown.equals(card))//unselect
				{
					cardShown = null;
					operator.setConfirmEnabled(false);
				}
				else//change
				{
					cardShown = card;
					operator.setCardOnHandSelected(card, true);
				}
			}
			else //select new
			{
				cardShown = card;
				operator.setCardOnHandSelected(card, true);
				operator.setConfirmEnabled(true);
			}
		}
		else //source selecting card to dispose
		{
			if(cardDisposed != null)//unselect previous
			{
				operator.setCardOnHandSelected(cardDisposed, false);
				if(cardDisposed.equals(card))//unselect
				{
					cardDisposed = null;
					operator.setConfirmEnabled(false);
				}
				else//change
				{
					cardDisposed = card;
					operator.setCardOnHandSelected(card, true);
				}
			}
			else //select new
			{
				cardDisposed = card;
				operator.setCardOnHandSelected(card, true);
				operator.setConfirmEnabled(true);
			}
		}
	}

	@Override
	public void onCancelledBy(PlayerOriginalClientComplete player) 
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
			if(cardDisposed != null)//cancel selection
			{
				player.setCardOnHandSelected(cardDisposed, false);
				player.setConfirmEnabled(false);
				cardDisposed = null;
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
	public void onConfirmedBy(PlayerOriginalClientComplete player) 
	{
		player.setOperation(null);
		if(sent && !shown)//confirm showing card
		{
			player.setCardOnHandSelected(cardShown, false);
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
			player.setCardOnHandSelected(cardDisposed, false);
			player.sendToMaster(new DisposalOfCards(source,cardDisposed,new Damage(1,Attack.FIRE,source,target,this.getNext())));
		}
	}
	@Override
	protected void playerOpEffect(PlayerOriginalClientComplete player) 
	{
		if(shown)
		{
			player.findMatch(target).showCard(cardShown);
		}
		if(!shown && player.isEqualTo(target))//showing card
		{
			if(player.getCardsOnHandCount() == 0)//cannot be attacked
			{
				player.sendToMaster(getNext());//continue
				return;
			}
			player.setAllCardsOnHandSelectable(true);
			player.setOperation(this);
		}
		else if(shown && player.isEqualTo(source))//choosing card to dispose
		{
			for(Card c : player.getCardsOnHand())
				if(c.getSuit() == cardShown.getSuit())
					player.setCardSelectable(c, true);
			player.setCancelEnabled(true);
			player.setOperation(this);
		}		
	}

}
