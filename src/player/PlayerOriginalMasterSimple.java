package player;

import core.Card;

public class PlayerOriginalMasterSimple extends PlayerOriginal
{
	private int cardsCount;
	public PlayerOriginalMasterSimple(String name, int position) 
	{
		super(name, position);
		cardsCount = 0;
	}
	@Override
	public void addCard(Card card)
	{
		cardsCount++;
	}

	@Override
	public void useCard(Card card)
	{
		cardsCount--;
	}

	@Override
	public void discardCard(Card card)
	{
		cardsCount--;
	}

	@Override
	public int getCardsOnHandCount()
	{
		return cardsCount;
	}
	@Override
	public void startDealing() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void endDealing() {
		// TODO Auto-generated method stub
		
	}
}
