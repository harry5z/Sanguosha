package cards.equipments.weapons;

import core.event.handlers.equipment.AncientNuggetFalchionAttackDamageEventHandler;
import core.player.PlayerCompleteServer;
import core.server.game.Game;

public class AncientNuggetFalchion extends Weapon {

	private static final long serialVersionUID = 3088418134391918826L;

	public AncientNuggetFalchion(int num, Suit suit, int id) {
		super(2, num, suit, id);
	}

	@Override
	public String getName() {
		return "Ancient Nugget Falchion";
	}
	
	@Override
	public void onEquipped(Game game, PlayerCompleteServer owner) {
		game.registerEventHandler(new AncientNuggetFalchionAttackDamageEventHandler(owner));
	}
	
	@Override
	public void onUnequipped(Game game, PlayerCompleteServer owner) {
		game.removeEventHandler(new AncientNuggetFalchionAttackDamageEventHandler(owner));
	}

}
