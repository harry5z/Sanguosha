package cards.equipments.weapons;

import core.event.handlers.equipment.IcySwordAbilityCheckEventHandler;
import core.player.PlayerCompleteServer;
import core.server.game.GameEventRegistrar;

public class IcySword extends Weapon {

	private static final long serialVersionUID = 5010996802567818570L;

	public IcySword(int num, Suit suit, int id) {
		super(2, num, suit, id);
	}

	@Override
	public String getName() {
		return "Icy Sword";
	}
	
	@Override
	public void onEquipped(GameEventRegistrar game, PlayerCompleteServer owner) {
		game.registerEventHandler(new IcySwordAbilityCheckEventHandler(owner));
	}
	
	@Override
	public void onUnequipped(GameEventRegistrar game, PlayerCompleteServer owner) {
		game.removeEventHandler(new IcySwordAbilityCheckEventHandler(owner));
	}

}
