package listener;

import core.Card;

public interface CardOnHandListener 
{

	public void onCardAdded(Card card);
	public void onCardRemoved(Card card);
}
