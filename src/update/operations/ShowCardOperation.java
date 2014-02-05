package update.operations;

import cards.Card;
import player.PlayerOriginal;
import player.PlayerOriginalClientComplete;
import core.Framework;
import core.PlayerInfo;

public class ShowCardOperation implements Operation
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2861496807858561557L;
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
		if(player.matches(target))
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
