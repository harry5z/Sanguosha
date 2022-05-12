package core.player;

import java.util.Set;

import core.event.handlers.basic.RequestUseCardEventHandler;
import core.event.handlers.instant.PlayerCardSelectionEventHandler;
import core.event.handlers.turn.DefaultEndTurnEventHandler;
import core.server.game.Game;
import exceptions.server.game.InvalidPlayerCommandException;

public class PlayerCompleteServer extends PlayerComplete {

	public PlayerCompleteServer(String name, int position) {
		super(name, position);
	}

	@Override
	public void useAttack(Set<? extends Player> targets) throws InvalidPlayerCommandException {
		if (getAttackUsed() >= getAttackLimit()) {
			throw new InvalidPlayerCommandException("Attack used exceeds attack limit");
		}
		
		int targetLimit = this.getAttackTargetLimit();
		if (targets.size() > targetLimit) {
			throw new InvalidPlayerCommandException("Number of targets (" + targets.size() + ") exceeds limit (" + targetLimit +")");
		}
		super.useAttack(targets);
	}
	
	@Override
	public void useWine() throws InvalidPlayerCommandException {
		if (getWineUsed() >= getWineLimit()) {
			throw new InvalidPlayerCommandException("Wine used exceeds wine limit");
		}
		super.useWine();
	}
	
	@Override
	public void setAttackLimit(int limit) throws InvalidPlayerCommandException {
		if (limit < 0) {
			throw new InvalidPlayerCommandException("Attack limit cannot be smaller than 0");
		}
		super.setAttackLimit(limit);
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
	
	public void onGameReady(Game game) {
		/* setup event listeners */
		game.registerEventHandler(new RequestUseCardEventHandler(this));
		game.registerEventHandler(new PlayerCardSelectionEventHandler(this));
		game.registerEventHandler(new DefaultEndTurnEventHandler(this));
		// let the hero and its skills register themselves in the game
		this.getHero().onGameReady(game, this);
	}
}
