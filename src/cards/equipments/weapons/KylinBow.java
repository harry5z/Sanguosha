package cards.equipments.weapons;

public class KylinBow extends Weapon {

	private static final long serialVersionUID = -5406945570873385619L;

	public KylinBow(int num, Suit suit) {
		super(5, num, suit);
	}

	@Override
	public String getName() {
		return "Kylin Bow";
	}

}
