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
	public UnequipGameController(Game game, PlayerCompleteServer player, EquipmentType type) {
		super(game);
		this.player = player;
		this.type = type;
	}

	@Override
	public void proceed() {
		switch(this.stage) {
			case DISCARD_EQUIPMENT:
				Equipment equipment = this.player.getEquipment(this.type);
				try {
					this.player.unequip(this.type);
					equipment.onUnequipped(this.game, this.player);
					this.stage = this.stage.nextStage();
					this.game.getGameController().proceed();
				} catch (InvalidPlayerCommandException e) {
					e.printStackTrace();
				}
				break;
			case HERO_ABILITY:
				this.stage = this.stage.nextStage();
				this.proceed();
				break;
			case ITEM_ABILITY:
				try {
					this.game.emit(new UnequipItemAbilityEvent(this.player, this.type, this));
					this.stage = this.stage.nextStage();
					this.proceed();
				} catch (GameFlowInterruptedException e) {
					e.resume();
				}
				break;
			case END:
				this.onUnloaded();
				this.game.getGameController().proceed();
				break;
		}
	}
	
	public void setStage(UnequipStage stage) {
		this.stage = stage;
	}

	@Override
	protected UnequipStage getInitialStage() {
		return UnequipStage.DISCARD_EQUIPMENT;
	}

}
