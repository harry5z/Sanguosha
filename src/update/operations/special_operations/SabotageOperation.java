package update.operations.special_operations;

import player.PlayerClientComplete;
import player.PlayerClientSimple;
import player.PlayerOriginal;
import update.DisposalOfCards;
import update.Unequip;
import update.Update;
import update.UseOfCards;
import utils.OperationUtil;
import cards.Card;
import cards.equipments.Equipment;
import core.PlayerInfo;

public class SabotageOperation extends SpecialOperation
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7847731828305501888L;
	private PlayerInfo source;
	private Card sabotage;
	private PlayerInfo target;

	
	public SabotageOperation(PlayerInfo turnPlayer, Card sabotage, Update next) 
	{
		super(next, turnPlayer);
		this.source = turnPlayer;
		this.sabotage = sabotage;
		target = null;
	}

	@Override
	public String getName() 
	{
		return "Sabotage";
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
				player.getGameListener().setMessage("Choose a card to dispose");
				player.getGameListener().onDisplayCardSelectionPane(player.findMatch(target), true, true, true);
			}
			else
				player.sendToMaster(getNext());
		}
	}

	@Override
	public void onPlayerSelected(PlayerClientComplete operator,	PlayerOriginal player) 
	{
		target = OperationUtil.selectTarget(operator, target, player);
	}

	@Override
	public void onCardSelected(PlayerClientComplete operator, Card card) 
	{
		operator.getGameListener().clearMessage();
		operator.setOperation(null);
		if(card == null) //card on hand
		{
			operator.sendToMaster(new DisposalOfCards(target,getNext()));// dispose a random card on hand
		}
		else if(card instanceof Equipment) //TODO what if equipment used in decision area?
		{
			Equipment e = (Equipment)card;
			PlayerClientSimple t = operator.findMatch(target);
			if(t.isEquipped(e.getEquipmentType()) &&
					e.equals(t.getEquipment(e.getEquipmentType())))//actually equipped
			{
				t.unequip(e.getEquipmentType());
				operator.sendToMaster(new Unequip(target,getNext(),e,true));
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
		player.setCardOnHandSelected(sabotage, false);			
	}

	@Override
	public void onConfirmedBy(PlayerClientComplete player) 
	{
		onCancelledBy(player);
		player.setOperation(null);
		player.sendToMaster(new UseOfCards(source,sabotage,this));		
	}

	@Override
	public PlayerInfo getCurrentTarget() 
	{
		return target;
	}

}
