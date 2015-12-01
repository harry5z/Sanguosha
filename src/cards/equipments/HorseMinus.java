package cards.equipments;

public class HorseMinus extends Equipment {

	private static final long serialVersionUID = 4263828194081932793L;
	
	private String name;

	public HorseMinus(int num, Suit suit, int id, String name) {
		super(num, suit, EquipmentType.HORSEMINUS, id);
		this.name = name;
	}

	public int getDistance() {
		return -1;
	}

	@Override
	public String getName() {
		return name;
	}
}
