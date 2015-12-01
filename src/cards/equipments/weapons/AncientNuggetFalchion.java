package cards.equipments.weapons;

public class AncientNuggetFalchion extends Weapon {

	private static final long serialVersionUID = 3088418134391918826L;

	public AncientNuggetFalchion(int num, Suit suit, int id) {
		super(2, num, suit, id);
	}

	@Override
	public String getName() {
		return "Ancient Nugget Falchion";
	}

}
