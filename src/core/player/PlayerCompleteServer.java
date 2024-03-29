package core.player;

import core.server.game.GameInternal;
import exceptions.server.game.InvalidPlayerCommandException;

public class PlayerCompleteServer extends PlayerComplete {

	public PlayerCompleteServer(String name, int position) {
		super(name, position);
	}

	@Override
	public void useAttack() throws InvalidPlayerCommandException {
		if (getAttackUsed() >= getAttackLimit()) {
			throw new InvalidPlayerCommandException("Attack used exceeds attack limit");
		}
		super.useAttack();
	}
	
	@Override
	public void useWine() throws InvalidPlayerCommandException {
		if (getWineUsed() >= getWineLimit()) {
			throw new InvalidPlayerCommandException("Wine used exceeds wine limit");
		}
		super.useWine();
	}
	
	@Override
	public void setAttackUsed(int amount) throws InvalidPlayerCommandException {
		if (amount < 0) {
			throw new InvalidPlayerCommandException("Attack used cannot be smaller than 0");
		}
		super.setAttackUsed(amount);
	}
	
	@Override
	public void setWineUsed(int amount) throws InvalidPlayerCommandException {
		if (amount < 0) {
			throw new InvalidPlayerCommandException("Wine used cannot be smaller than 0");
		}
		super.setWineUsed(amount);
	}
	
	public void onGameReady(GameInternal game) {
		// let the hero and its skills register themselves in the game
		this.getHero().onGameReady(game, this);
	}

}
