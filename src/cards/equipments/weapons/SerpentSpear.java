package cards.equipments.weapons;

import core.client.GamePanel;
import core.client.game.listener.equipment.SerpentSpearAttackReactionEventListener;
import core.client.game.listener.equipment.SerpentSpearInitiateAttackEventListener;

public class SerpentSpear extends Weapon {

	private static final long serialVersionUID = -4325875230988779058L;

	public SerpentSpear(int num, Suit suit, int id) {
		super(3, num, suit, id);
	}

	@Override
	public String getName() {
		return "Serpent Spear";
	}
	
	@Override
	public void onEquipped(GamePanel panel) {
		panel.registerEventListener(new SerpentSpearInitiateAttackEventListener());
		panel.registerEventListener(new SerpentSpearAttackReactionEventListener());
	}
	
	@Override
	public void onUnequipped(GamePanel panel) {
		panel.removeEventListener(new SerpentSpearInitiateAttackEventListener());
		panel.removeEventListener(new SerpentSpearAttackReactionEventListener());
	}

}
