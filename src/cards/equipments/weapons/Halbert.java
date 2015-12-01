package cards.equipments.weapons;

public class Halbert extends Weapon {

	private static final long serialVersionUID = 120028584621186883L;

	public Halbert(int num, Suit suit, int id) {
		super(4, num, suit, id);
	}

	@Override
	public String getName() {
		return "Halbert";
	}

}
