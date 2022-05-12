package utils;

import java.io.Serializable;

import core.player.PlayerCompleteServer;
import core.server.game.controllers.specials.delayed.AbstractDelayedArbitrationController;
import core.server.game.controllers.specials.delayed.LightningArbitrationController;
import core.server.game.controllers.specials.delayed.OblivionArbitrationController;
import core.server.game.controllers.specials.delayed.StarvationArbitrationController;

public enum DelayedType implements Serializable {
	LIGHTNING(target -> new LightningArbitrationController(target)),
	OBLIVION(target -> new OblivionArbitrationController(target)),
	STARVATION(target -> new StarvationArbitrationController(target));
	
	@FunctionalInterface
	private static interface SupplierFunction {
	    public AbstractDelayedArbitrationController construct(PlayerCompleteServer target);
	}
	
	private final SupplierFunction supplier;
	
	private DelayedType(SupplierFunction supplier) {
		this.supplier = supplier;
	}
	
	public AbstractDelayedArbitrationController getController(PlayerCompleteServer target) {
		return this.supplier.construct(target);
	}
}
