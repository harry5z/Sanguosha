package commands.operations.special;

import java.util.Random;

import commands.Command;
import commands.Unequip;
import commands.UseOfCards;
import player.PlayerComplete;
import player.PlayerSimple;
import player.PlayerOriginal;
import cards.Card;
import cards.equipments.Equipment;
import core.PlayerInfo;

public class StealOperation extends SpecialOperation
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 324760187182837241L;
	private PlayerInfo source;
	private Card steal;
	private PlayerInfo target;
	private boolean hand;
	private boolean equipment;
	private Card stolen;
	
	public StealOperation(PlayerInfo turnPlayer, Card steal, Command next) 
	{
		super(next, turnPlayer);
		this.source = turnPlayer;
		this.steal = steal;
		this.target = null;
		this.hand = false;
		this.equipment = false;
		this.stolen = null;
	}

	@Override
	public String getName() 
	{
		return "Steal";
	}

	@Override
	protected void playerOpEffect(PlayerComplete player) 
	{
		if(player.equals(source))
		{
			PlayerSimple t = player.findMatch(target);
			if(t.getHandCount() != 0 || t.isEquipped())//TODO or if has decision area cards
			{
				player.setOperation(this);
				player.getGameListener().setMessage("Choose a card to steal");
				player.getGameListener().onDisplayCardSelectionPane(player.findMatch(target), true, true, true);
			}
			else
				player.sendToServer(getNext());
		}		
	}
	
	@Override
	protected void playerOpAfter(PlayerComplete player)
	{
		if(!(hand || equipment))//neutralized
		{
			super.playerOpAfter(player);
			return;
		}
		if(hand && stolen == null)//target card on hand stolen
		{
			if(player.equals(target))
			{
				Random rand = new Random();
				stolen = player.getCardsOnHand().get(rand.nextInt(player.getHandCount()));
				player.sendToServer(this);
			}
			return;
		}
		if(hand)//equipped
			player.findMatch(target).removeCardFromHand(stolen);//TODO can be card in dicision area
		
		if(player.equals(source))
			player.addCard(stolen);
		else
			player.findMatch(source).addCard(stolen);
		if(player.equals(source)) //has stolen card
		{
			player.sendToServer(getNext());
		}
	}
	@Override
	public void onPlayerSelected(PlayerComplete operator, PlayerOriginal player) 
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
		operator.getGameListener().clearMessage();
		operator.setOperation(null);
		if(card == null) //card on hand
		{
			this.nextStage();
			this.hand = true;
			operator.sendToServer(this);// dispose a random card on hand
		}
		else if(card instanceof Equipment) //TODO what if equipment used in decision area?
		{
			Equipment e = (Equipment)card;
			PlayerSimple t = operator.findMatch(target);
			if(t.isEquipped(e.getEquipmentType()) &&
					e.equals(t.getEquipment(e.getEquipmentType())))//actually equipped
			{
				t.unequip(e.getEquipmentType());
				this.nextStage();
				this.stolen = e;
				this.equipment = true;
				this.insertNext(new Unequip(target,getNext(),e,false));
				operator.sendToServer(this);
			}
		}		
	}

	@Override
	public void onCancelledBy(PlayerComplete player) 
	{
		player.setAllTargetsSelectableIncludingSelf(false);
		player.setConfirmEnabled(false);
		player.setCancelEnabled(false);
		if(target != null)
			player.unselectTarget(target);
		player.setCardOnHandSelected(steal, false);	
	}

	@Override
	public void onConfirmedBy(PlayerComplete player)
	{
		onCancelledBy(player);
		player.setOperation(null);
		player.sendToServer(new UseOfCards(source,steal,this));		
	}

	@Override
	public PlayerInfo getCurrentTarget() 
	{
		return target;
	}

}
