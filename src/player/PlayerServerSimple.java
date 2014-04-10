package player;

import cards.Card;

/**
 * master side player information
 * @author Harry
 *
 */
public class PlayerServerSimple extends PlayerOriginal
{
	private int cardsCount;
	public PlayerServerSimple(String name) 
	{
		super(name);
		cardsCount = 0;
	}
	public PlayerServerSimple(String name, int position) 
	{
		super(name,position);
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
	public void takeDamage(int amount)
	{
		changeHealthCurrentBy(-amount);
	}
	@Override
	public void removeCardFromHand(Card card)
	{
		cardsCount--;
	}
}
