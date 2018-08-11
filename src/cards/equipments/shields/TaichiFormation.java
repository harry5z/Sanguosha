package cards.equipments.shields;

public class TaichiFormation extends Shield {

	private static final long serialVersionUID = -6729114905115919334L;

	public TaichiFormation(int num, Suit suit, int id) {
		super(num, suit, id);
	}

	@Override
	public String getName() {
		return "Taichi Formation";
	}

}
