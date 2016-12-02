package cards.equipments.shields;

import cards.Card;
import core.event.handlers.damage.SilverLionCheckDamageEventHandler;
import core.event.handlers.equipment.SilverLionUnequipEventHandler;
import core.player.PlayerCompleteServer;
import core.server.game.Game;

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
	public boolean mustReactTo(Card card) {
		return true;
	}
	
	@Override
	public void onEquipped(Game game, PlayerCompleteServer owner) {
		game.registerEventHandler(new SilverLionCheckDamageEventHandler(owner));
		game.registerEventHandler(new SilverLionUnequipEventHandler(owner));
	}
	
	@Override
	public void onUnequipped(Game game, PlayerCompleteServer owner) {
		game.removeEventHandler(new SilverLionCheckDamageEventHandler(owner));
		game.removeEventHandler(new SilverLionUnequipEventHandler(owner));
	}

}
