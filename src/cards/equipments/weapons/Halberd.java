package cards.equipments.weapons;

import core.client.GamePanel;
import core.client.game.listener.equipment.HalberdClientEventListener;
import core.player.PlayerCompleteServer;
import core.player.query_listener.HalberdPlayerAttackTargetLimitQueryListener;
import core.server.game.GameEventRegistrar;

public class Halberd extends Weapon {

	private static final long serialVersionUID = 120028584621186883L;

	public Halberd(int num, Suit suit, int id) {
		super(4, num, suit, id);
	}

	@Override
	public String getName() {
		return "Halberd";
	}
	
	@Override
	public void onEquipped(GameEventRegistrar game, PlayerCompleteServer owner) {
		owner.registerPlayerStatusQueryListener(new HalberdPlayerAttackTargetLimitQueryListener());
	}
	
	@Override
	public void onEquipped(GamePanel panel) {
		panel.registerEventListener(new HalberdClientEventListener());
	}
	
	@Override
	public void onUnequipped(GameEventRegistrar game, PlayerCompleteServer owner) {
		owner.removePlayerStatusQueryListener(new HalberdPlayerAttackTargetLimitQueryListener());
	}
	
	@Override
	public void onUnequipped(GamePanel panel) {
		panel.removeEventListener(new HalberdClientEventListener());
	}

}
