package cards.equipments.weapons;

public class FeatheredFan extends Weapon {

	private static final long serialVersionUID = -6161969993742726749L;

	public FeatheredFan(int num, Suit suit) {
		super(4, num, suit);
	}

	@Override
	public String getName() {
		return "FeatheredFan";
	}

}
