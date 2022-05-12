package cards.equipments.shields;

import core.event.handlers.equipment.IronShieldAttackTargetEuipmentCheckEventHandler;
import core.player.PlayerCompleteServer;
import core.server.game.GameEventRegistrar;

public class IronShield extends Shield {

	private static final long serialVersionUID = 4370802087723597065L;

	public IronShield(int num, Suit suit, int id) {
		super(num, suit, id);
	}

	@Override
	public String getName() {
		return "Iron Shield";
	}
	
	@Override
	public void onEquipped(GameEventRegistrar game, PlayerCompleteServer owner) {
		game.registerEventHandler(new IronShieldAttackTargetEuipmentCheckEventHandler(owner));
	}
	
	@Override
	public void onUnequipped(GameEventRegistrar game, PlayerCompleteServer owner) {
		game.removeEventHandler(new IronShieldAttackTargetEuipmentCheckEventHandler(owner));
	}
	
}
