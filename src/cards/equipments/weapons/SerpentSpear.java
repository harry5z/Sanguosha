package cards.equipments.weapons;

public class SerpentSpear extends Weapon {

	private static final long serialVersionUID = -4325875230988779058L;

	public SerpentSpear(int num, Suit suit, int id) {
		super(3, num, suit, id);
	}

	@Override
	public String getName() {
		return "SerpentSpear";
	}

}
