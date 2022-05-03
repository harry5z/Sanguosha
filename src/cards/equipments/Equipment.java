package cards.equipments;

import cards.Card;
import core.GameState;
import core.client.GamePanel;
import core.client.game.operations.Operation;
import core.client.game.operations.machanics.EquipOperation;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import ui.game.interfaces.Activatable;

/**
 * The "Equipment" type of cards, consisting of all equipments
 * 
 * @author Harry
 *
 */
public abstract class Equipment extends Card {

	private static final long serialVersionUID = 1L;
	
	private EquipmentType equipmentType;// 1.Weapon 2.Shield 3.Horse+ 4.Horse-
	private boolean equipped;

	public enum EquipmentType {
		WEAPON, SHIELD, HORSEPLUS, HORSEMINUS
	}

	public Equipment(int num, Suit suit, EquipmentType equipmentType, int id) {
		super(num, suit, CardType.EQUIPMENT, id);
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
	public Operation generateOperation(Activatable source) {
		return new EquipOperation(source);
	}

	@Override
	public boolean isActivatable(GameState game) {
		return true;
	}
	
	/**
	 * Server side on-equip setup, e.g. registering Event Handlers
	 * @param game
	 * @param owner
	 */
	public void onEquipped(Game game, PlayerCompleteServer owner) {}
	
	/**
	 * Client side on-equip setup, e.g. registering Event Handlers
	 * @param panel
	 */
	public void onEquipped(GamePanel panel) {}
	
	/**
	 * Server side on-unequip setup, e.g. unregistering Event Handlers
	 * @param game
	 * @param owner
	 */
	public void onUnequipped(Game game, PlayerCompleteServer owner) {}
	
	/**
	 * Client side on-unequip setup, e.g. unregistering Event Handlers
	 * @param panel
	 */
	public void onUnequipped(GamePanel panel) {}
	
	/**
	 * 
	 * Called when the abilities of the equipment is (re)activated
	 * @param game
	 * @param owner
	 */
	public void onActivated(Game game, PlayerCompleteServer owner) {
		this.onEquipped(game, owner);
	}
	
	/**
	 * Called when the abilities of the equipment is suppressed
	 * @param game
	 * @param owner
	 */
	public void onDeactivated(Game game, PlayerCompleteServer owner) {
		this.onUnequipped(game, owner);
	}
	
}
