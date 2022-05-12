package cards.equipments.weapons;

import core.event.handlers.equipment.KylinBowAbilityCheckEventHandler;
import core.player.PlayerCompleteServer;
import core.server.game.GameEventRegistrar;

public class KylinBow extends Weapon {

	private static final long serialVersionUID = -5406945570873385619L;

	public KylinBow(int num, Suit suit, int id) {
		super(5, num, suit, id);
	}

	@Override
	public String getName() {
		return "Kylin Bow";
	}
	
	@Override
	public void onEquipped(GameEventRegistrar game, PlayerCompleteServer owner) {
		game.registerEventHandler(new KylinBowAbilityCheckEventHandler(owner));
	}
	
	@Override
	public void onUnequipped(GameEventRegistrar game, PlayerCompleteServer owner) {
		game.removeEventHandler(new KylinBowAbilityCheckEventHandler(owner));
	}

}
