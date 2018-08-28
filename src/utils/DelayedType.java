package utils;

import java.io.Serializable;

import core.player.PlayerCompleteServer;
import core.server.game.Game;
import core.server.game.controllers.TurnGameController;
import core.server.game.controllers.specials.delayed.AbstractDelayedArbitrationController;
import core.server.game.controllers.specials.delayed.LightningArbitrationController;
import core.server.game.controllers.specials.delayed.OblivionArbitrationController;

public enum DelayedType implements Serializable {
	LIGHTNING((game, target, turn) -> new LightningArbitrationController(game, target, turn)),
	OBLIVION((game, target, turn) -> new OblivionArbitrationController(game, target, turn)),
	STARVATION(null);
	
	@FunctionalInterface
	private static interface SupplierFunction {
	    public AbstractDelayedArbitrationController construct(Game game, PlayerCompleteServer target, TurnGameController turn);
	}
	
	private final SupplierFunction supplier;
	
	private DelayedType(SupplierFunction supplier) {
		this.supplier = supplier;
	}
	
	public AbstractDelayedArbitrationController getController(Game game, PlayerCompleteServer target, TurnGameController turn) {
		return this.supplier.construct(game, target, turn);
	}
}
