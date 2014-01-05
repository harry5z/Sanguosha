package events.special_events;

import player.PlayerOriginal;
import player.PlayerOriginalClientComplete;
import update.Damage;
import core.Card;
import core.PlayerInfo;
import core.Update;

public class DuelOperation extends SpecialOperation
{
	private PlayerInfo source;
	private PlayerInfo target;
	private Damage damage;
	private Card duel;
	private Card attack;
	
	public DuelOperation(PlayerInfo source,Card duel,Update next)
	{
		super(next);
		this.duel = duel;
		this.source = source;
		this.target = null;
		this.attack = null;
		damage = new Damage(duel,source,target,next);
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
	public void onCardSelected(PlayerOriginalClientComplete operator, Card card) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCancelledBy(PlayerOriginalClientComplete player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConfirmedBy(PlayerOriginalClientComplete player) {
		// TODO Auto-generated method stub
		
	}


	
}
