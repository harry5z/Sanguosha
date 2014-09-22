package commands.operations.special;

import commands.Command;
import commands.DisposalOfCards;
import commands.Unequip;
import commands.UseOfCards;
import player.PlayerComplete;
import player.PlayerSimple;
import player.PlayerOriginal;
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

	
	public SabotageOperation(PlayerInfo turnPlayer, Card sabotage, Command next) 
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
	protected void playerOpEffect(PlayerComplete player)
	{
		if(player.equals(source))
		{
			PlayerSimple t = player.findMatch(target);
			if(t.getHandCount() != 0 || t.isEquipped())//TODO or if has decision area cards
			{
				player.setOperation(this);
				player.getGameListener().setMessage("Choose a card to dispose");
				player.getGameListener().onDisplayCardSelectionPane(player.findMatch(target), true, true, true);
			}
			else
				player.sendToServer(getNext());
		}
	}

	@Override
	public void onPlayerSelected(PlayerComplete operator,	PlayerOriginal player) 
	{
		target = OperationUtil.selectTarget(operator, target, player);
	}

	@Override
	public void onCardSelected(PlayerComplete operator, Card card) 
	{
		operator.getGameListener().clearMessage();
		operator.setOperation(null);
		if(card == null) //card on hand
		{
			operator.sendToServer(new DisposalOfCards(target,getNext()));// dispose a random card on hand
		}
		else if(card instanceof Equipment) //TODO what if equipment used in decision area?
		{
			Equipment e = (Equipment)card;
			PlayerSimple t = operator.findMatch(target);
			if(t.isEquipped(e.getEquipmentType()) &&
					e.equals(t.getEquipment(e.getEquipmentType())))//actually equipped
			{
				t.unequip(e.getEquipmentType());
				operator.sendToServer(new Unequip(target,getNext(),e,true));
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
		player.setCardOnHandSelected(sabotage, false);			
	}

	@Override
	public void onConfirmedBy(PlayerComplete player) 
	{
		onCancelledBy(player);
		player.setOperation(null);
		player.sendToServer(new UseOfCards(source,sabotage,this));		
	}

	@Override
	public PlayerInfo getCurrentTarget() 
	{
		return target;
	}

}
