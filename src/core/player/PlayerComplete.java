package core.player;

import java.util.ArrayList;
import java.util.List;

import cards.Card;
import exceptions.server.game.InvalidPlayerCommandException;
import listeners.game.PlayerStatusListener;

/**
 * client side complete implementation of player, used as player himself
 * 
 * @author Harry
 *
 */
public class PlayerComplete extends PlayerSimple {
	// ******** in-game properties ***********
	private List<Card> cardsOnHand;
	private int attackLimit;// limit of attacks can be used in one TURN_DEAL, by
							// default 1
	private int attackUsed;// number of attacks already used this TURN_DEAL
	private volatile int wineLimit;// limit of wines can be used in on TURN_DEAL, by
							// default 1
	private volatile int wineUsed;// number of wines already used this TURN_DEAL
	private volatile boolean isWineUsed;// whether wine is used
	private volatile boolean wineEffective; // whether wine is currently effective

	// private settings

	// in-game interactive properties
	private List<Card> cardsUsedThisTurn;

	private PlayerStatusListener statusListener;

	public PlayerComplete(String name, int position) {
		super(name, position);
		init();
	}

	private void init() {
		cardsOnHand = new ArrayList<Card>();

		cardsUsedThisTurn = new ArrayList<Card>();
		// init in-game interactive properties
		attackLimit = 1;
		attackUsed = 0;
		wineLimit = 1;
		wineUsed = 0;
		isWineUsed = false;
		wineEffective = false;
	}
	
	public void registerPlayerStatusListener(PlayerStatusListener listener) {
		this.statusListener = listener;
	}

	public List<Card> getCardsOnHand() {
		return cardsOnHand;
	}

	@Override
	public void addCard(Card card) {
		cardsOnHand.add(card);
		super.addCard(card);
	}

	@Override
	public void removeCardFromHand(Card card) throws InvalidPlayerCommandException {
		if (!cardsOnHand.contains(card)) {
			throw new InvalidPlayerCommandException("removeCardFromHand: Card " + card + " is not on player's hand");
		}
		cardsOnHand.remove(card);
		super.removeCardFromHand(card);
	}
	
	@Override
	public void useCard(Card card) throws InvalidPlayerCommandException {
		if (!cardsOnHand.contains(card)) {
			throw new InvalidPlayerCommandException("useCard: Card " + card + " is not on player's hand");
		}
		cardsOnHand.remove(card);
		super.useCard(card);
	}
	
	@Override
	public void discardCard(Card card) throws InvalidPlayerCommandException {
		if (!cardsOnHand.contains(card)) {
			throw new InvalidPlayerCommandException("discardCard: Card " + card + " is not on player's hand");
		}
		cardsOnHand.remove(card);
		super.discardCard(card);
	}

	// ************** methods related to in-game properties ****************
	@Override
	public void flip() {
		super.flip();
		statusListener.onFlip(isFlipped());
	}
	
	public void setAttackLimit(int limit) throws InvalidPlayerCommandException {
		if (limit != getAttackLimit()) {
			attackLimit = limit;
			statusListener.onSetAttackLimit(limit);
		}
	}

	public void setAttackUsed(int amount) throws InvalidPlayerCommandException {
		if (amount != getAttackUsed()) {
			attackUsed = amount;
			statusListener.onSetAttackUsed(amount);
		}
	}

	public void useAttack() throws InvalidPlayerCommandException {
		attackUsed++;
		statusListener.onAttackUsed();
	}

	public void setWineUsed(int amount) throws InvalidPlayerCommandException {
		if (amount != getWineUsed()) {
			wineUsed = amount;
			if (wineUsed == 0) {
				isWineUsed = false;
			}
			statusListener.onSetWineUsed(amount);
		}
	}
	
	public void useWine() throws InvalidPlayerCommandException {
		wineUsed++;
		isWineUsed = true;
		wineEffective = true;
		statusListener.onWineUsed();
	}
	
	public boolean isWineEffective() {
		return this.wineEffective;
	}
	
	public void resetWineEffective() {
		if (this.wineEffective) {
			this.wineEffective = false;
			statusListener.onResetWineEffective();
		}
	}
	
	public int getAttackUsed() {
		return attackUsed;
	}

	public int getAttackLimit() {
		return attackLimit;
	}

	public boolean isWineUsed() {
		return isWineUsed;
	}

	public int getWineUsed() {
		return wineUsed;
	}

	public int getWineLimit() {
		return wineLimit;
	}

}
