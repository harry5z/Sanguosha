package cards.equipments.weapons;

import core.client.GamePanel;
import core.player.PlayerCompleteServer;
import core.player.query_listener.ChuKoNuPlayerAttackLimitQueryListener;
import core.server.game.Game;

public class ChuKoNu extends Weapon {

	private static final long serialVersionUID = 1620384786441396718L;

	public ChuKoNu(int num, Suit suit, int id) {
		super(1, num, suit, id);
	}

	@Override
	public String getName() {
		return "ChuKoNu";
	}
	
	@Override
	public void onEquipped(Game game, PlayerCompleteServer owner) {
		owner.registerPlayerStatusQueryListener(new ChuKoNuPlayerAttackLimitQueryListener());
	}
	
	@Override
	public void onEquipped(GamePanel panel) {
		panel.getContent().getSelf().registerPlayerStatusQueryListener(new ChuKoNuPlayerAttackLimitQueryListener());
	}
	
	@Override
	public void onUnequipped(Game game, PlayerCompleteServer owner) {
		owner.removePlayerStatusQueryListener(new ChuKoNuPlayerAttackLimitQueryListener());
	}
	
	@Override
	public void onUnequipped(GamePanel panel) {
		panel.getContent().getSelf().removePlayerStatusQueryListener(new ChuKoNuPlayerAttackLimitQueryListener());
	}
}
