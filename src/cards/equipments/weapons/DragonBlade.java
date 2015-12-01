package cards.equipments.weapons;

public class DragonBlade extends Weapon {

	private static final long serialVersionUID = -3981887339364479189L;

	public DragonBlade(int num, Suit suit, int id) {
		super(3, num, suit, id);
	}

	@Override
	public String getName() {
		return "DragonBlade";
	}

}
