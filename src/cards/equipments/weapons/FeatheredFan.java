package cards.equipments.weapons;

import core.event.handlers.equipment.FeatheredFanWeaponAbilitiesCheckEventHandler;
import core.player.PlayerCompleteServer;
import core.server.game.GameEventRegistrar;

public class FeatheredFan extends Weapon {

	private static final long serialVersionUID = -6161969993742726749L;

	public FeatheredFan(int num, Suit suit, int id) {
		super(4, num, suit, id);
	}

	@Override
	public String getName() {
		return "Feathered Fan";
	}
	
	@Override
	public void onEquipped(GameEventRegistrar game, PlayerCompleteServer owner) {
		game.registerEventHandler(new FeatheredFanWeaponAbilitiesCheckEventHandler(owner));
	}
	
	@Override
	public void onUnequipped(GameEventRegistrar game, PlayerCompleteServer owner) {
		game.removeEventHandler(new FeatheredFanWeaponAbilitiesCheckEventHandler(owner));
	}

}
