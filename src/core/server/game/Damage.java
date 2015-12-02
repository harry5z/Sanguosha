package core.server.game;

import java.util.ArrayList;
import java.util.List;

import cards.Card;
import core.player.PlayerCompleteServer;

/**
 * Damage
 * 
 * @author Harry
 *
 */
public class Damage {
	
	public enum Element {
		NORMAL, FIRE, THUNDER;
	}

	private Element element;
	private int amount;
	private List<Card> cardsCausingDamage;
	private PlayerCompleteServer source;
	private PlayerCompleteServer target;

	/**
	 * Default setup of damage, used as simple damage caused by 1 card:
	 * <ol>
	 * <li>Require: target != null.
	 * <li>element = Attack.NORMAL
	 * <li>amount = 1
	 * <li>cardsCausingDamage is the same as cardCausingDamage
	 * <ol>
	 * 
	 * @param cardUsed
	 *            : the card that both represents and causes the damage
	 * @param source
	 *            : source of damage
	 * @param target
	 *            : target of damage
	 */
	public Damage(Card cardUsed, PlayerCompleteServer source, PlayerCompleteServer target) {
		this.element = Element.NORMAL;
		this.amount = 1;
		this.source = source;
		this.target = target;
		this.cardsCausingDamage = new ArrayList<Card>(1);
		this.cardsCausingDamage.add(cardUsed);
	}
	
	public Damage(PlayerCompleteServer source, PlayerCompleteServer target) {
		this.element = Element.NORMAL;
		this.amount = 1;
		this.source = source;
		this.target = target;
		this.cardsCausingDamage = new ArrayList<Card>();
	}
	
	/**
	 * Setup of non-card damage (caused by skills, etc.) Require : target !=
	 * null
	 * 
	 * @param amount
	 *            : amount of damage
	 * @param element
	 *            : one of Attack.Fire/Attack.Normal/Attack.Thunder
	 * @param source
	 *            : source of damage
	 * @param target
	 *            : target of damage
	 */
	public Damage(int amount, Element element, PlayerCompleteServer source, PlayerCompleteServer target) {
		this.amount = amount;
		this.element = element;
		this.source = source;
		this.target = target;
		this.cardsCausingDamage = null;

	}

	/**
	 * element == one of Fire/Thunder/Normal
	 * 
	 * @param element
	 */
	public void setElement(Element element) {
		this.element = element;
	}

	public Element getElement() {
		return element;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getAmount() {
		return amount;
	}

	/**
	 * change the list of cards that cause the damage
	 * 
	 * @param cards
	 */
	public void setCardsCausingDamage(List<Card> cards) {
		cardsCausingDamage = cards;
	}

	/**
	 * list can be null, which represents damage without using any card
	 * 
	 * @return list of cards that cause the damage
	 */
	public List<Card> getCardsCausingDamage() {
		return cardsCausingDamage;
	}

	/**
	 * source can be null, which represents "source-less" damage
	 * 
	 * @return
	 */
	public PlayerCompleteServer getSource() {
		return source;
	}

	/**
	 * there must be a target
	 * 
	 * @return
	 */
	public PlayerCompleteServer getTarget() {
		return target;
	}
	
	public void apply() {
		target.changeHealthCurrentBy(-amount);
	}

}
