package cards.equipments.weapons;

public class FeatheredFan extends Weapon {

	private static final long serialVersionUID = -6161969993742726749L;

	public FeatheredFan(int num, Suit suit, int id) {
		super(4, num, suit, id);
	}

	@Override
	public String getName() {
		return "FeatheredFan";
	}

}
