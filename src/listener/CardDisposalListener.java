package listener;

import java.util.ArrayList;

import core.Card;

public interface CardDisposalListener 
{
	public void onCardUsed(Card card);
	public void onCardsUsed(ArrayList<Card> cards);
}
