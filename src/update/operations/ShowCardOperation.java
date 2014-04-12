package update.operations;

import player.PlayerClientComplete;
import player.PlayerOriginal;
import cards.Card;
import cards.Card.CardType;
import core.Framework;
import core.PlayerInfo;

public class ShowCardOperation extends Operation
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2861496807858561557L;
	private PlayerInfo target;
	private Card cardShown;
	private boolean anyType;
	private CardType type;
	private String name;
	
	public ShowCardOperation(PlayerInfo target)
	{
		super(null);
		this.target = target;
		anyType = true;
		name = null;
		cardShown = null;
	}
	@Override
	public void frameworkOperation(Framework framework) 
	{
		framework.sendToAllClients(this);
	}

	@Override
	public void playerOperation(PlayerClientComplete player) 
	{
		if(player.matches(target))
		{
			player.setOperation(this);
			if(anyType)
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
	public void onPlayerSelected(PlayerClientComplete operator, PlayerOriginal player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCardSelected(PlayerClientComplete operator, Card card) 
	{
		if(cardShown != null)//unselect previous
		{
			operator.getGameListener().setCardSelected(cardShown, false);
			if(cardShown.equals(card))//unselect
			{
				cardShown = null;
				operator.getGameListener().setConfirmEnabled(false);
			}
			else//change
			{
				cardShown = card;
				operator.getGameListener().setCardSelected(card, true);
			}
		}
		else //select new
		{
			cardShown = card;
			operator.getGameListener().setCardSelected(card, true);
			operator.getGameListener().setConfirmEnabled(true);
		}
	}

	@Override
	public void onCancelledBy(PlayerClientComplete player) 
	{
		
	}

	@Override
	public void onConfirmedBy(PlayerClientComplete player) 
	{
		
	}

}
