package cards.equipments.shields;

import core.event.handlers.equipment.TaichiFormationDodgeEquipmentCheckEventHandler;
import core.player.PlayerCompleteServer;
import core.server.game.GameEventRegistrar;

public class TaichiFormation extends Shield {

	private static final long serialVersionUID = 1L;

	public TaichiFormation(int num, Suit suit, int id) {
		super(num, suit, id);
	}

	@Override
	public String getName() {
		return "Taichi Formation";
	}
	
	@Override
	public void onEquipped(GameEventRegistrar game, PlayerCompleteServer owner) {
		game.registerEventHandler(new TaichiFormationDodgeEquipmentCheckEventHandler(owner));
	}
	
	@Override
	public void onUnequipped(GameEventRegistrar game, PlayerCompleteServer owner) {
		game.removeEventHandler(new TaichiFormationDodgeEquipmentCheckEventHandler(owner));
	}

}
