package cards.equipments.weapons;

import core.event.handlers.equipment.AxeWeaponAbilitiesCheckEventHandler;
import core.player.PlayerCompleteServer;
import core.server.game.GameEventRegistrar;

public class Axe extends Weapon {

	private static final long serialVersionUID = 1L;

	public Axe(int num, Suit suit, int id) {
		super(3, num, suit, id);
	}

	@Override
	public String getName() {
		return "Axe";
	}
	
	@Override
	public void onEquipped(GameEventRegistrar game, PlayerCompleteServer owner) {
		game.registerEventHandler(new AxeWeaponAbilitiesCheckEventHandler(owner));
	}

	@Override
	public void onUnequipped(GameEventRegistrar game, PlayerCompleteServer owner) {
		game.removeEventHandler(new AxeWeaponAbilitiesCheckEventHandler(owner));
	}
}
