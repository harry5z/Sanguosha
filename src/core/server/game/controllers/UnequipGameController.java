package core.server.game.controllers;

import cards.equipments.Equipment;
import cards.equipments.Equipment.EquipmentType;
import core.event.game.UnequipItemAbilityEvent;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.InvalidPlayerCommandException;
import utils.EnumWithNextStage;

public class UnequipGameController extends AbstractGameController {
	
	public static enum UnequipStage implements EnumWithNextStage<UnequipStage> {
		DISCARD_EQUIPMENT,
		HERO_ABILITY,
		ITEM_ABILITY,
		END,
	}
	
	private final PlayerCompleteServer player;
	private final EquipmentType type;
	private UnequipStage stage;

	public UnequipGameController(Game game, PlayerCompleteServer player, EquipmentType type) {
		super(game);
		this.player = player;
		this.type = type;
		this.stage = UnequipStage.DISCARD_EQUIPMENT;
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

}
