package events;

import player.PlayerOriginal;
import player.PlayerOriginalClientComplete;
import core.Card;
import core.Framework;
import core.Operation;
import core.PlayerInfo;

public class ShowCardOperation implements Operation
{

	private PlayerInfo target;
	private Card cardShown;
	private boolean any;
	private int type;
	private String name;
	
	public ShowCardOperation(PlayerInfo target)
	{
		this.target = target;
		any = true;
		name = null;
		cardShown = null;
	}
	@Override
	public void frameworkOperation(Framework framework) 
	{
		framework.sendToAllClients(this);
	}

	@Override
	public void playerOperation(PlayerOriginalClientComplete player) 
	{
		if(player.isEqualTo(target))
		{
			player.setOperation(this);
			if(any)
			{
				player.setAllCardsOnHandSelectable(true);
			}
			else if(name != null)
				player.setCardSelectableByName(name, true);
			else 
				player.setCardSelectableByType(type, true);
		}
	}

	@Override
	public void onPlayerSelected(PlayerOriginalClientComplete operator,
			PlayerOriginal player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCardSelected(PlayerOriginalClientComplete operator, Card card) 
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

	@Override
	public void onCancelledBy(PlayerOriginalClientComplete player) 
	{
		
	}

	@Override
	public void onConfirmedBy(PlayerOriginalClientComplete player) 
	{
		
	}

}
