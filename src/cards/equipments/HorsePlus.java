package cards.equipments;

public class HorsePlus extends Equipment {

	private static final long serialVersionUID = 6981646924294901512L;
	
	private String name;

	public HorsePlus(int num, Suit suit, int id, String name) {
		super(num, suit, EquipmentType.HORSEPLUS, id);
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
