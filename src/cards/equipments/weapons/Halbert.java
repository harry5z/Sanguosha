package cards.equipments.weapons;

public class Halbert extends Weapon {

	private static final long serialVersionUID = 120028584621186883L;

	public Halbert(int num, Suit suit) {
		super(4, num, suit);
	}

	@Override
	public String getName() {
		return "Halbert";
	}

}
