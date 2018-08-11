package cards.equipments.shields;

import core.event.handlers.equipment.IronShieldAttackEventHandler;
import core.player.PlayerCompleteServer;
import core.server.game.Game;

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
	public void onEquipped(Game game, PlayerCompleteServer owner) {
		game.registerEventHandler(new IronShieldAttackEventHandler(owner));
	}
	
	@Override
	public void onUnequipped(Game game, PlayerCompleteServer owner) {
		game.removeEventHandler(new IronShieldAttackEventHandler(owner));
	}
}
