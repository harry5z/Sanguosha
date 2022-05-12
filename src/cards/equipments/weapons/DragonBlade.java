package cards.equipments.weapons;

import core.event.handlers.equipment.DragonBladeAbilitiesCheckEventHandler;
import core.player.PlayerCompleteServer;
import core.server.game.GameEventRegistrar;

public class DragonBlade extends Weapon {

	private static final long serialVersionUID = -3981887339364479189L;

	public DragonBlade(int num, Suit suit, int id) {
		super(3, num, suit, id);
	}

	@Override
	public String getName() {
		return "Dragon Blade";
	}
	
	@Override
	public void onEquipped(GameEventRegistrar game, PlayerCompleteServer owner) {
		game.registerEventHandler(new DragonBladeAbilitiesCheckEventHandler(owner));
	}
	
	public void onUnequipped(GameEventRegistrar game, PlayerCompleteServer owner) {
		game.removeEventHandler(new DragonBladeAbilitiesCheckEventHandler(owner));
	}

}
