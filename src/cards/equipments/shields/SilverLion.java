package cards.equipments.shields;

import core.event.handlers.equipment.SilverLionCheckDamageEventHandler;
import core.event.handlers.equipment.SilverLionUnequipEventHandler;
import core.player.PlayerCompleteServer;
import core.server.game.GameEventRegistrar;

public class SilverLion extends Shield {

	private static final long serialVersionUID = -4821532886423359596L;

	public SilverLion(int num, Suit suit, int id) {
		super(num, suit, id);
	}

	@Override
	public String getName() {
		return "Silver Lion";
	}
	
	@Override
	public void onEquipped(GameEventRegistrar game, PlayerCompleteServer owner) {
		game.registerEventHandler(new SilverLionCheckDamageEventHandler(owner));
		game.registerEventHandler(new SilverLionUnequipEventHandler(owner));
	}
	
	@Override
	public void onUnequipped(GameEventRegistrar game, PlayerCompleteServer owner) {
		game.removeEventHandler(new SilverLionCheckDamageEventHandler(owner));
		game.removeEventHandler(new SilverLionUnequipEventHandler(owner));
	}
	
}
