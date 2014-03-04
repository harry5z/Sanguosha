package update.operations.special_operations;

import java.util.Random;

import player.PlayerClientComplete;
import player.PlayerClientSimple;
import player.PlayerOriginal;
import update.DisposalOfCards;
import update.Unequip;
import update.Update;
import update.UseOfCards;
import cards.Card;
import cards.Card.CardType;
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
	
	public StealOperation(PlayerInfo turnPlayer, Card steal, Update next) 
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
	protected void playerOpEffect(PlayerClientComplete player) 
	{
		if(player.matches(source))
		{
			PlayerClientSimple t = player.findMatch(target);
			if(t.getCardsOnHandCount() != 0 || t.isEquipped())//TODO or if has decision area cards
			{
				player.setOperation(this);
				player.getGameListener().onSetMessage("Choose a card to steal");
				player.getGameListener().onDisplayCardSelectionPane(player.findMatch(target), true, true, true);
			}
			else
				player.sendToMaster(getNext());
		}		
	}
	
	@Override
	protected void playerOpAfter(PlayerClientComplete player)
	{
		if(!(hand || equipment))//neutralized
		{
			super.playerOpAfter(player);
			return;
		}
		if(hand && stolen == null)//target card on hand stolen
		{
			if(player.matches(target))
			{
				Random rand = new Random();
				stolen = player.getCardsOnHand().get(rand.nextInt(player.getCardsOnHandCount()));
				player.sendToMaster(this);
			}
			return;
		}
		if(hand)//equipped
			player.findMatch(target).removeCardFromHand(stolen);//TODO can be card in dicision area
		
		if(player.matches(source))
			player.addCard(stolen);
		else
			player.findMatch(source).addCard(stolen);
		if(player.matches(source)) //has stolen card
		{
			player.sendToMaster(getNext());
		}
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
		operator.getGameListener().onClearMessage();
		operator.setOperation(null);
		if(card == null) //card on hand
		{
			this.nextStage();
			this.hand = true;
			operator.sendToMaster(this);// dispose a random card on hand
		}
		else if(card instanceof Equipment) //TODO what if equipment used in decision area?
		{
			Equipment e = (Equipment)card;
			PlayerClientSimple t = operator.findMatch(target);
			if(t.isEquipped(e.getEquipmentType()) &&
					e.equals(t.getEquipment(e.getEquipmentType())))//actually equipped
			{
				t.unequip(e.getEquipmentType());
				this.nextStage();
				this.stolen = e;
				this.equipment = true;
				this.insertNext(new Unequip(target,getNext(),e,false));
				operator.sendToMaster(this);
			}
		}		
	}

	@Override
	public void onCancelledBy(PlayerClientComplete player) 
	{
		player.setAllTargetsSelectableIncludingSelf(false);
		player.setConfirmEnabled(false);
		player.setCancelEnabled(false);
		if(target != null)
			player.unselectTarget(target);
		player.setCardOnHandSelected(steal, false);	
	}

	@Override
	public void onConfirmedBy(PlayerClientComplete player)
	{
		onCancelledBy(player);
		player.setOperation(null);
		player.sendToMaster(new UseOfCards(source,steal,this));		
	}

}
