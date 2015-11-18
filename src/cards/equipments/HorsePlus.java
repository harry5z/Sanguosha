package cards.equipments;

public class HorsePlus extends Equipment {

	private static final long serialVersionUID = 6981646924294901512L;
	
	private String name;

	public HorsePlus(int num, Suit suit, String name) {
		super(num, suit, EquipmentType.HORSEPLUS);
		this.name = name;
	}

	public int getDistance() {
		return 1;
	}

	@Override
	public String getName() {
		return name;
	}
}
