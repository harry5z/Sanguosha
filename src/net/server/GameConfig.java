package net.server;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import core.Deck.DeckPack;
import core.HeroPool.HeroPack;

/**
 * This class defines the setup of a game, e.g. game speed, card packs, hero
 * packs, etc.
 * 
 * @author Harry
 * 
 */
public class GameConfig implements Serializable {
	private static final long serialVersionUID = -7498104838348562081L;
	private int gameSpeed;
	private Set<DeckPack> deckConfig;
	private Set<HeroPack> heroConfig;

	/**
	 * Default configuration:
	 * <ul>
	 * <li>Game Speed: 10 seconds</li>
	 * <li>Decks: Standard + EX + Battle</li>
	 * <li>Heroes: All allowed</li>
	 * </ul>
	 */
	public GameConfig() {
		this.gameSpeed = 10;
		Collections.addAll(this.deckConfig = new HashSet<DeckPack>(), DeckPack.values());
		Collections.addAll(this.heroConfig = new HashSet<HeroPack>(), HeroPack.values());
	}

	/**
	 * Non-default configuration for room
	 * 
	 * @param gameSpeed : how many seconds for each player to deal cards
	 * @param decks : deck sets to use
	 * @param heroes : hero sets to use
	 */
	public GameConfig(int gameSpeed, Set<DeckPack> decks, Set<HeroPack> heroes) {
		this.gameSpeed = gameSpeed;
		this.deckConfig = new HashSet<DeckPack>(decks);
		this.heroConfig = new HashSet<HeroPack>(heroes);
	}

	/**
	 * Require: speed >= 5
	 * 
	 * @param speed
	 */
	public void setGameSpeed(int speed) {
		if (speed >= 5)
			this.gameSpeed = speed;
	}

	/**
	 * Add / remove a pack of cards
	 * 
	 * @param pack : pack to be added / removed
	 * @param adding : true for adding, false for removing
	 */
	public void modifyDeckPacks(DeckPack pack, boolean adding) {
		if (adding)
			this.deckConfig.add(pack);
		else
			this.deckConfig.remove(pack);
	}

	/**
	 * Add / remove a pack of heroes
	 * 
	 * @param pack : pack to be added / removed
	 * @param adding : true for adding, false for removing
	 */
	public void modifyHeroPacks(HeroPack pack, boolean adding) {
		if (adding)
			this.heroConfig.add(pack);
		else
			this.heroConfig.remove(pack);
	}

	public int getGameSpeed() {
		return this.gameSpeed;
	}

	public Set<DeckPack> getDeckPacks() {
		return Collections.unmodifiableSet(this.deckConfig);
	}

	public Set<HeroPack> getHeroPacks() {
		return Collections.unmodifiableSet(this.heroConfig);
	}
}
