package cards.equipments;

import core.client.GamePanel;
import core.player.PlayerCompleteServer;
import core.server.game.Game;

public class HorseMinus extends Equipment {

	private static final long serialVersionUID = 4263828194081932793L;
	
	private String name;

	public HorseMinus(int num, Suit suit, int id, String name) {
		super(num, suit, EquipmentType.HORSEMINUS, id);
		this.name = name;
	}

	public int getDistance() {
		return -1;
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
