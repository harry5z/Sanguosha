package cards.equipments;

import core.client.GamePanel;
import core.player.PlayerCompleteServer;
import core.server.game.Game;

public class HorsePlus extends Equipment {

	private static final long serialVersionUID = 6981646924294901512L;
	
	private String name;

	public HorsePlus(int num, Suit suit, int id, String name) {
		super(num, suit, EquipmentType.HORSEPLUS, id);
		this.name = name;
	}

	public int getDistance() {
		return 1;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void onEquipped(Game game, PlayerCompleteServer owner) {
		// nothing to do		
	}

	@Override
	public void onEquipped(GamePanel panel) {
		// nothing to do		
	}
}
