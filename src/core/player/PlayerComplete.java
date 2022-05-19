package core.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cards.Card;
import core.player.query.PlayerAttackLimitQuery;
import core.player.query.PlayerAttackTargetLimitQuery;
import core.player.query.PlayerStatusQuery;
import core.player.query_listener.PlayerStatusQueryListener;
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
	@SuppressWarnings("unused")
	@Deprecated
	private int attackLimit;// limit of attacks can be used in one TURN_DEAL, by
							// default 1
	private int attackUsed;// number of attacks already used this TURN_DEAL
	private volatile int wineLimit;// limit of wines can be used in on TURN_DEAL, by
							// default 1
	private volatile int wineUsed;// number of wines already used this TURN_DEAL
	private volatile boolean isWineUsed;// whether wine is used
	private final Map<PlayerState, Integer> stateCounters;
	private final Map<PlayerState, Integer> defaultStateCounters;
	
	private Map<Class<? extends PlayerStatusQuery>, Set<PlayerStatusQueryListener<? extends PlayerStatusQuery>>> playerQueryListeners;

	// private settings

	private PlayerStatusListener statusListener;

	public PlayerComplete(String name, int position) {
		super(name, position);
		cardsOnHand = new ArrayList<Card>();
		this.playerQueryListeners = new HashMap<>();

		// init in-game interactive properties
		attackLimit = 1;
		attackUsed = 0;
		wineLimit = 1;
		wineUsed = 0;
		isWineUsed = false;
		this.stateCounters = new HashMap<>();
		this.defaultStateCounters = new HashMap<>();
	}
	
	public void registerPlayerStatusListener(PlayerStatusListener listener) {
		this.statusListener = listener;
	}
	
	public void registerPlayerStatusQueryListener(PlayerStatusQueryListener<? extends PlayerStatusQuery> listener) {
		if (this.playerQueryListeners.containsKey(listener.getQueryClass())) {
			this.playerQueryListeners.get(listener.getQueryClass()).add(listener);
		} else {
			this.playerQueryListeners.put(listener.getQueryClass(), new HashSet<>(Set.of(listener)));
		}
	}
	
	public void removePlayerStatusQueryListener(PlayerStatusQueryListener<? extends PlayerStatusQuery> listener) {
		if (!this.playerQueryListeners.containsKey(listener.getQueryClass())) {
			return;
		}
		
		this.playerQueryListeners.get(listener.getQueryClass()).remove(listener);
	}

	@SuppressWarnings("unchecked")
	public <Q extends PlayerStatusQuery> void query(Q query) {
		if (!this.playerQueryListeners.containsKey(query.getClass())) {
			return;
		}
		
		for (PlayerStatusQueryListener<? extends PlayerStatusQuery> listener : this.playerQueryListeners.get(query.getClass())) {
			((PlayerStatusQueryListener<Q>) listener).onQuery(query, this);
		}
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
	
	@Override
	public void chain() {
		super.chain();
		this.statusListener.onChained(this.isChained());
	}
	
	@Override
	public void setChained(boolean chained) {
		super.setChained(chained);
		this.statusListener.onChained(chained);
	}
	
	@Deprecated
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

	public void useAttack(Set<? extends Player> targets) throws InvalidPlayerCommandException {
		attackUsed++;
		statusListener.onAttackUsed(targets);
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
	
	@Override
	public void useWine() throws InvalidPlayerCommandException {
		wineUsed++;
		isWineUsed = true;
		super.useWine();
	}
	
	public int getAttackUsed() {
		return attackUsed;
	}

	public int getAttackLimit() {
		PlayerAttackLimitQuery query = new PlayerAttackLimitQuery(1);
		this.query(query);
		return query.getLimit();
	}
	
	public int getAttackTargetLimit() {
		PlayerAttackTargetLimitQuery query = new PlayerAttackTargetLimitQuery(1);
		this.query(query);
		return query.getLimit();
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
	
	public void registerDefaultPlayerState(PlayerState key, int value) {
		this.defaultStateCounters.put(key, value);
		this.updatePlayerState(key, value);
	}
	
	public void resetPlayerStates() {
		for (Map.Entry<PlayerState, Integer> entry : this.defaultStateCounters.entrySet()) {
			this.updatePlayerState(entry.getKey(), entry.getValue());
		}
	}
	
	public void updatePlayerState(PlayerState key, int value) {
		this.stateCounters.put(key, value);
		this.statusListener.onPlayerStateUpdated(key, value);
	}
	
	public int getPlayerState(PlayerState key) {
		return this.stateCounters.get(key);
	}

}
