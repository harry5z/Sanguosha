package core.server.game.controllers.mechanics;

import cards.equipments.Equipment;
import cards.equipments.Equipment.EquipmentType;
import core.event.game.UnequipItemAbilityEvent;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
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
	}

	@Override
	protected void handleStage(Game game, UnequipStage stage) throws GameFlowInterruptedException {
		switch(stage) {
			case DISCARD_EQUIPMENT:
				Equipment equipment = this.player.getEquipment(this.type);
				try {
					this.nextStage();
					this.player.unequip(this.type);
					equipment.onUnequipped(game, this.player);
				} catch (InvalidPlayerCommandException e) {
					e.printStackTrace();
				}
				break;
			case HERO_ABILITY:
				// nothing here yet
				this.nextStage();
				break;
			case ITEM_ABILITY:
				this.nextStage();
				game.emit(new UnequipItemAbilityEvent(this.player, this.type, this));
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
