package cards.equipments;

import cards.Card;
import commands.server.ingame.EquipInGameServerCommand;
import commands.server.ingame.InGameServerCommand;
import core.GameState;
import core.client.GamePanel;
import core.client.game.operations.Operation;
import core.client.game.operations.mechanics.EquipOperation;
import core.player.PlayerCompleteServer;
import core.server.game.GameEventRegistrar;
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
	public void onEquipped(GameEventRegistrar game, PlayerCompleteServer owner) {}
	
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
	public void onUnequipped(GameEventRegistrar game, PlayerCompleteServer owner) {}
	
	/**
	 * Client side on-unequip setup, e.g. unregistering Event Handlers
	 * @param panel
	 */
	public void onUnequipped(GamePanel panel) {}
	
	@Override
	public Class<? extends InGameServerCommand> getAllowedDealPhaseResponseType() {
		return EquipInGameServerCommand.class;
	}
	
}
