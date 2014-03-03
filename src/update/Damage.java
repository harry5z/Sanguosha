package update;

import java.util.ArrayList;

import cards.Card;
import cards.equipments.Equipment.EquipmentType;
import player.PlayerClientComplete;
import update.operations.NearDeathOperation;
import core.*;

/**
 * Damage update
 * @author Harry
 *
 */
public class Damage extends Update
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4543012418271735342L;
	public static final byte TARGET_HERO_SKILLS = 1;
	public static final byte TARGET_EQUIPMENT_SKILLS = 2;
	public static final byte TARGET_CHECK_CHAINED = 3;
	public static final byte TARGET_DAMAGE = 4;
	public static final byte SOURCE_HERO_SKILLS_AFTER_DAMAGE = 5;
	public static final byte TARGET_HERO_SKILLS_AFTER_DAMAGE = 6;
	
	public enum Element {
		NORMAL, FIRE, THUNDER;
	}
	
	private Element element;
	private int amount;
	private ArrayList<Card> cardsCausingDamage;
	private Card cardUsedAs;
	private PlayerInfo source;
	private PlayerInfo target;
	private byte stage;
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
	 * @param next : next update
	 */
	public Damage(Card cardUsed, PlayerInfo source, PlayerInfo target, Update next)
	{
		super(next);
		this.element = Element.NORMAL;
		this.amount = 1;
		this.source = source;
		this.target = target;
		this.cardUsedAs = cardUsed;
		this.cardsCausingDamage = new ArrayList<Card>(1);
		this.cardsCausingDamage.add(cardUsedAs);
		this.stage = TARGET_HERO_SKILLS;
	}
	/**
	 * Setup of non-card damage (caused by skills, etc.)
	 * Require : target != null
	 * @param amount : amount of damage
	 * @param element : one of Attack.Fire/Attack.Normal/Attack.Thunder
	 * @param source : source of damage
	 * @param target : target of damage
	 */
	public Damage(int amount, Element element, PlayerInfo source, PlayerInfo target, Update next)
	{
		super(next);
		this.amount = amount;
		this.element = element;
		this.source = source;
		this.target = target;
		this.cardsCausingDamage = null;
		this.cardUsedAs = null;
		this.stage = TARGET_HERO_SKILLS;

	}
	/**
	 * element == one of Fire/Thunder/Normal
	 * @param element
	 */
	public void setElement(Element element)
	{
		this.element = element;
	}
	public Element getElement()
	{
		return element;
	}
	public void setAmount(int amount)
	{
		this.amount = amount;
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
	public PlayerInfo getSource()
	{
		return source;
	}
	/**
	 * there must be a target
	 * @return
	 */
	public PlayerInfo getTarget()
	{
		return target;
	}
	private void next(PlayerClientComplete player)
	{
		stage++;
		player.sendToMaster(this);		
	}
	@Override
	public void playerOperation(PlayerClientComplete player)
	{
		System.out.println(player.getName()+" Damage ");
		if(stage == TARGET_DAMAGE)
			player.findMatch(target).takeDamage(amount);
		if(player.matches(target))
		{
			if(stage == TARGET_HERO_SKILLS)
			{
				next(player);
				return;
			}
			else if(stage == TARGET_EQUIPMENT_SKILLS)
			{
				if(player.isEquipped(EquipmentType.SHIELD))
				{
					player.getShield().modifyDamage(this);
				}
				next(player);
				return;
			}
			else if(stage == TARGET_CHECK_CHAINED)
			{
				next(player);
				return;
			}
			else if(stage == TARGET_DAMAGE)
			{	
				if(player.isDying())
				{
					stage++;
					player.sendToMaster(new NearDeathOperation(player.getCurrentStage().getSource(),source,target,this));
				}
				else
					next(player);
				return;
			}
			else if(stage == TARGET_HERO_SKILLS_AFTER_DAMAGE)
			{
				player.sendToMaster(getNext());
				return;
			}
		}
		if(player.matches(source))
		{
			if(stage == SOURCE_HERO_SKILLS_AFTER_DAMAGE)
				next(player);
		}
	}
	@Override
	public void frameworkOperation(Framework framework) 
	{
		framework.sendToAllClients(this);
	}

}
