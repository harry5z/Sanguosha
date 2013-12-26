package update;

import core.Card;
import core.Player;

public class RequireUseOfCardsByName extends SourceTargetAmount
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4272961307334352267L;
	private String cardName;
	
	public RequireUseOfCardsByName(Player source,Player target,Card card,int amount)
	{
		this.setSource(source);
		this.setTarget(target);
		this.cardName = card.getName();
		this.setAmount(amount);
	}
	public RequireUseOfCardsByName(Player source,Card card,int amount)
	{
		this.setSource(source);
		this.cardName = card.getName();
		this.setAmount(amount);
	}
	/**
	 * the most common setup of RequireUseOfCards
	 * @param source
	 * @param cardName
	 * @param type
	 * @param amount
	 */
	public RequireUseOfCardsByName(Player source,String cardName,int amount)
	{
		this.setSource(source);
		this.cardName = cardName;
		this.setAmount(amount);
	}
	/**
	 * blank setup
	 */
	public RequireUseOfCardsByName()
	{
		
	}
	public void setCardName(String name)
	{
		cardName = name;
	}
	@Override
	public void playerOperation(Player player)
	{
		player.setCardSelectableByName(cardName, true);
	}

}
