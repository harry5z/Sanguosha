package cards.equipments;

import cards.Card;
import core.client.game.operations.EquipOperation;
import core.client.game.operations.Operation;
import player.PlayerComplete;

/**
 * The "Equipment" type of cards, consisting of all equipments
 * 
 * @author Harry
 *
 */
public abstract class Equipment extends Card {

	private static final long serialVersionUID = 5968539700238484665L;
	
	private EquipmentType equipmentType;// 1.Weapon 2.Shield 3.Horse+ 4.Horse-
	private boolean equipped;

	public enum EquipmentType {
		WEAPON, SHIELD, HORSEPLUS, HORSEMINUS
	}

	public Equipment(int num, Suit suit, EquipmentType equipmentType) {
		super(num, suit, CardType.EQUIPMENT);
		this.equipped = false;
		this.equipmentType = equipmentType;
	}

	public void setEquipped(boolean b) {
		equipped = b;
	}

	public boolean isEquipped() {
		return equipped;
	}

	public EquipmentType getEquipmentType() {
		return equipmentType;
	}

	@Override
	public Operation generateOperation() {
		return new EquipOperation();
	}

	@Override
	public boolean isActivatableBy(PlayerComplete player) {
		return true;
	}
}
