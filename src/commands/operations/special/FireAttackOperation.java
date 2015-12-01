package commands.operations.special;

import commands.Command;
import commands.DisposalOfCards;
import commands.game.server.ingame.UseOfCardsInGameServerCommand;
import cards.Card;
import core.player.PlayerComplete;
import core.player.PlayerInfo;
import core.player.PlayerOriginal;
import core.server.game.Damage;
import core.server.game.Damage.Element;

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
	
	public FireAttackOperation(PlayerComplete player, Card fireAttack,Command next)
	{
		super(next,player.getCurrentStage().getSource());
		this.source = player.getPlayerInfo();
		this.target = null;
		this.fireAttack = fireAttack;
		shown = false;
		sent = false;
	}

	@Override
	public void onPlayerSelected(PlayerComplete operator,PlayerOriginal player)
	{
		if(target == null)//select target
		{
			target = player.getPlayerInfo();
			operator.selectTarget(target);//target selected
			operator.setConfirmEnabled(true);//can confirm
		}
		else
		{
			if(player.equals(target))//cancel
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
	public void onCardSelected(PlayerComplete operator, Card card)
	{
		cardSelectedAsReaction(operator, card);
	}

	@Override
	public void onCancelledBy(PlayerComplete player) 
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
				player.sendToServer(getNext());
			}
		}
	}

	@Override
	public void onConfirmedBy(PlayerComplete player) 
	{
		player.setOperation(null);
		if(sent && !shown)//confirm showing card
		{
			player.setCardOnHandSelected(reactionCard, false);
			player.setAllCardsOnHandSelectable(false);
			shown = true;
			player.sendToServer(this);
		}
		else if(!sent && !shown)//confirm fire attack
		{
			sent = true;
			player.unselectTarget(target);
			player.setCardOnHandSelected(fireAttack, false);
			player.setAllTargetsSelectableIncludingSelf(false);
			player.sendToServer(new UseOfCardsInGameServerCommand(player.getPlayerInfo(),fireAttack,this));
		}
		else if(sent && shown)//confirm damage
		{
			player.setCardOnHandSelected(reactionCard, false);
			player.sendToServer(new DisposalOfCards(source,reactionCard,new Damage(1,Element.FIRE,source,target,getNext())));
		}
	}
	@Override
	protected void playerOpEffect(PlayerComplete player) 
	{
		if(shown)
		{
			player.findMatch(target).showCard(reactionCard);
		}
		if(!shown && player.equals(target))//showing card
		{
			if(player.getHandCount() == 0)//cannot be attacked
			{
				player.sendToServer(getNext());//continue
				return;
			}
			player.setAllCardsOnHandSelectable(true);
			player.setOperation(this);
			player.getGameListener().setMessage("You are being Fire Attacked, please show a card on hand");
		}
		else if(shown && player.equals(source))//choosing card to dispose
		{
			for(Card c : player.getCardsOnHand())
				if(c.getSuit() == reactionCard.getSuit())
					player.setCardSelectable(c, true);
			reactionCard = null;
			player.setCancelEnabled(true);
			player.setOperation(this);
			player.getGameListener().setMessage("Choose a card with the same suit to cause damage");
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
