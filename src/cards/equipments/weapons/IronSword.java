package cards.equipments.weapons;

import core.event.handlers.equipment.IronSwordWeaponAbilitiesCheckEventHandler;
import core.player.PlayerCompleteServer;
import core.server.game.Game;

public class IronSword extends Weapon {

	private static final long serialVersionUID = -4813948682586646901L;

	public IronSword(int num, Suit suit, int id) {
		super(2, num, suit, id);
	}

	@Override
	public String getName() {
		return "Iron Sword";
	}
	
	@Override
	public void onEquipped(Game game, PlayerCompleteServer owner) {
		game.registerEventHandler(new IronSwordWeaponAbilitiesCheckEventHandler(owner));
	}
	
	@Override
	public void onUnequipped(Game game, PlayerCompleteServer owner) {
		game.removeEventHandler(new IronSwordWeaponAbilitiesCheckEventHandler(owner));
	}

}
