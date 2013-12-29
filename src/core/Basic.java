package core;

import player.PlayerOriginalClientSimple;


public abstract class Basic extends Card
{
	public Basic(int num, int suit)
	{
		super(num, suit, Card.BASIC);
	}
	public Basic(int color)
	{
		super(color,Card.BASIC);
	}
	public Basic()
	{
		super(Card.COLORLESS,Card.BASIC);
	}
	/**
	 * Most cards of type basic do not have such operation (except attack)
	 * @param player
	 */
	@Override
	public void onPlayerSelected(PlayerOriginalClientSimple player)
	{
		
	}
}
