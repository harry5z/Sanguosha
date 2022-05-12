package cards.equipments.weapons;

import core.event.handlers.equipment.AncientFalchionAttackDamageEventHandler;
import core.player.PlayerCompleteServer;
import core.server.game.GameEventRegistrar;

public class AncientFalchion extends Weapon {

	private static final long serialVersionUID = 3088418134391918826L;

	public AncientFalchion(int num, Suit suit, int id) {
		super(2, num, suit, id);
	}

	@Override
	public String getName() {
		return "Ancient Falchion";
	}
	
	@Override
	public void onEquipped(GameEventRegistrar game, PlayerCompleteServer owner) {
		game.registerEventHandler(new AncientFalchionAttackDamageEventHandler(owner));
	}
	
	@Override
	public void onUnequipped(GameEventRegistrar game, PlayerCompleteServer owner) {
		game.removeEventHandler(new AncientFalchionAttackDamageEventHandler(owner));
	}

}
