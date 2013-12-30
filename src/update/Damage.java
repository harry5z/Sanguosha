package update;

import java.util.ArrayList;

import player.PlayerOriginalClientComplete;
import basics.Attack;
import core.*;

public class Damage extends SourceTargetAmount
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4543012418271735342L;
	private int element;
	private int amount;
	private ArrayList<Card> cardsCausingDamage;
	private Card cardUsedAs;
	private PlayerInfo source;
	private PlayerInfo target;
	/**
	 * Default setup of damage, used as simple damage caused by 1 card:
	 * <ol>
	 * <li>Require: target != null.
	 * <li>element = Attack.NORMAL
	 * <li>amount = 1
	 * <li>cardsCausingDamage is the same as cardCausingDamage
	 * <ol>
	 * @param cardUsed : the card that both represents and causes the damage
	 * @param source : source of damage
	 * @param target : target of damage
	 */
	public Damage(Card cardUsed, PlayerInfo source, PlayerInfo target)
	{
		element = Attack.NORMAL;
		amount = 1;
		this.source = source;
		this.target = target;
		cardUsedAs = cardUsed;
		cardsCausingDamage = new ArrayList<Card>(1);
		cardsCausingDamage.add(cardUsedAs);
	}
	/**
	 * Setup of non-card damage (caused by skills, etc.)
	 * Require : target != null
	 * @param amount : amount of damage
	 * @param element : one of Attack.Fire/Attack.Normal/Attack.Thunder
	 * @param source : source of damage
	 * @param target : target of damage
	 */
	public Damage(int amount, int element, PlayerInfo source, PlayerInfo target)
	{
		this.amount = amount;
		this.element = element;
		this.source = source;
		this.target = target;
		cardsCausingDamage = null;
		cardUsedAs = null;
	}

	/**
	 * element == one of Fire/Thunder/Normal
	 * @param element
	 */
	public void setElement(int element)
	{
		this.element = element;
	}
	public int getElement()
	{
		return element;
	}
	public int getAmount()
	{
		return amount;
	}
	/**
	 * change the list of cards that cause the damage
	 * @param cards
	 */
	public void setCardsCausingDamage(ArrayList<Card> cards)
	{
		cardsCausingDamage = cards;
	}
	/**
	 * list can be null, which represents damage without using any card
	 * @return list of cards that cause the damage
	 */
	public ArrayList<Card> getCardsCausingDamage()
	{
		return cardsCausingDamage;
	}
	/**
	 * change the card that 
	 * @param card
	 */
	public void setCardUsedAs(Card card)
	{
		cardUsedAs = card;
	}
	/**
	 * can be null, which represents "non-card" damage
	 * @return cardUsedAs
	 */
	public Card getCardUsedAs()
	{
		return cardUsedAs;
	}
	/**
	 * source can be null, which represents "source-less" damage
	 * @return
	 */
	@Override
	public PlayerInfo getSource()
	{
		return source;
	}
	/**
	 * there must be a target
	 * @return
	 */
	@Override
	public PlayerInfo getTarget()
	{
		return target;
	}
	@Override
	public void playerOperation(PlayerOriginalClientComplete player)
	{
		if(target.equals(player))
		{
			player.takeDamage(this);
		}
		else
		{
			player.findMatch(target).takeDamage(this);
		}
		
	}
	@Override
	public void frameworkOperation(Framework framework) 
	{
		// TODO Auto-generated method stub
		
	}

}
