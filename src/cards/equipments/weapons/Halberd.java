package cards.equipments.weapons;

import core.client.GamePanel;
import core.client.game.listener.HalberdClientEventListener;
import core.heroes.Hero;
import core.player.PlayerCompleteServer;
import core.player.query_listener.HalberdPlayerAttackTargetLimitQueryListener;
import core.server.game.Game;

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
	public void onEquipped(Game game, PlayerCompleteServer owner) {
		owner.registerPlayerStatusQueryListener(new HalberdPlayerAttackTargetLimitQueryListener());
	}
	
	@Override
	public void onEquipped(GamePanel<Hero> panel) {
		panel.registerEventListener(new HalberdClientEventListener());
	}
	
	@Override
	public void onUnequipped(Game game, PlayerCompleteServer owner) {
		owner.removePlayerStatusQueryListener(new HalberdPlayerAttackTargetLimitQueryListener());
	}
	
	@Override
	public void onUnequipped(GamePanel<Hero> panel) {
		panel.removeEventListener(new HalberdClientEventListener());
	}

}
