package cards.equipments.weapons;

public class IcySword extends Weapon {

	private static final long serialVersionUID = 5010996802567818570L;

	public IcySword(int num, Suit suit, int id) {
		super(2, num, suit, id);
	}

	@Override
	public String getName() {
		return "Icy Sword";
	}

}
