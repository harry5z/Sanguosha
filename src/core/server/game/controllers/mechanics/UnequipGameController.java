package core.server.game.controllers.mechanics;

import cards.equipments.Equipment;
import cards.equipments.Equipment.EquipmentType;
import core.event.game.UnequipItemAbilityEvent;
import core.player.PlayerCompleteServer;
import core.server.game.BattleLog;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractGameController;
import core.server.game.controllers.GameControllerStage;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.InvalidPlayerCommandException;

public class UnequipGameController extends AbstractGameController<UnequipGameController.UnequipStage> {
	
	public static enum UnequipStage implements GameControllerStage<UnequipStage> {
		DISCARD_EQUIPMENT,
		HERO_ABILITY,
		ITEM_ABILITY,
		END,
	}
	
	private final PlayerCompleteServer player;
	private final EquipmentType type;
	private Equipment equipment;

	/**
	 * Unequip a certain equipment type from a player.
	 * 
	 * NOTE: this controller only removes an equipment from a player. The removed equipment must be 
	 * recycled, or moved to some player via another GameController
	 * 
	 * @param game
	 * @param player : the player to remove an equipment from
	 * @param type : the removed equipment type
	 */
	public UnequipGameController(PlayerCompleteServer player, EquipmentType type) {
		this.player = player;
		this.type = type;
		this.equipment = null;
	}

	@Override
	protected void handleStage(GameInternal game, UnequipStage stage) throws GameFlowInterruptedException {
		switch(stage) {
			case DISCARD_EQUIPMENT:
				this.equipment = this.player.getEquipment(this.type);
				try {
					this.nextStage();
					this.player.unequip(this.type);
					game.log(BattleLog.playerADidX(player, "lost equipment <b>" + equipment.getName() + "</b>"));
				} catch (InvalidPlayerCommandException e) {
					e.printStackTrace();
				}
				break;
			case HERO_ABILITY:
				// NOTE: By game design, no on-unequip hero ability shall be able to
				// trigger any interaction that may be affected by the unequipped
				// equipment, since equipment's listeners are only removed in the next stage
				this.nextStage();
				break;
			case ITEM_ABILITY:
				this.nextStage();
				game.emit(new UnequipItemAbilityEvent(this.player, this.type, this));
				this.equipment.onUnequipped(game, this.player);
				break;
			case END:
				break;
		}
	}

	@Override
	protected UnequipStage getInitialStage() {
		return UnequipStage.DISCARD_EQUIPMENT;
	}

}
