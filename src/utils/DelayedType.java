package utils;

import java.io.Serializable;

import core.player.PlayerCompleteServer;
import core.server.game.controllers.mechanics.TurnGameController;
import core.server.game.controllers.specials.delayed.AbstractDelayedArbitrationController;
import core.server.game.controllers.specials.delayed.LightningArbitrationController;
import core.server.game.controllers.specials.delayed.OblivionArbitrationController;
import core.server.game.controllers.specials.delayed.StarvationArbitrationController;

public enum DelayedType implements Serializable {
	LIGHTNING((target, turn) -> new LightningArbitrationController(target, turn)),
	OBLIVION((target, turn) -> new OblivionArbitrationController(target, turn)),
	STARVATION((target, turn) -> new StarvationArbitrationController(target, turn));
	
	@FunctionalInterface
	private static interface SupplierFunction {
	    public AbstractDelayedArbitrationController construct(PlayerCompleteServer target, TurnGameController turn);
	}
	
	private final SupplierFunction supplier;
	
	private DelayedType(SupplierFunction supplier) {
		this.supplier = supplier;
	}
	
	public AbstractDelayedArbitrationController getController(PlayerCompleteServer target, TurnGameController turn) {
		return this.supplier.construct(target, turn);
	}
}
