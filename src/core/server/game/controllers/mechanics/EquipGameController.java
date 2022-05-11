package core.server.game.controllers.mechanics;

import java.util.Set;

import cards.equipments.Equipment;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import core.server.game.controllers.AbstractGameController;
import core.server.game.controllers.GameControllerStage;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.InvalidPlayerCommandException;

public class EquipGameController extends AbstractGameController<EquipGameController.EquipStage> {

	public static enum EquipStage implements GameControllerStage<EquipStage> {
		UNEQUIP,
		EQUIP,
		END;
	}
	
	private final PlayerCompleteServer player;
	private final Equipment equipment;
	
	public EquipGameController(Game game, PlayerCompleteServer player, Equipment equipment) {
		super(game);
		this.player = player;
		this.equipment = equipment;
	}

	@Override
	protected void handleStage(EquipStage stage) throws GameFlowInterruptedException {
		switch (stage) {
			case UNEQUIP:
				try {
					// TODO: convert to controller
					this.player.removeCardFromHand(this.equipment);
				} catch (InvalidPlayerCommandException e) {
					
				}
				
				// discard old equipment (if any) at the same slot
				if (this.player.isEquipped(this.equipment.getEquipmentType())) {
					Equipment old  = this.player.getEquipment(this.equipment.getEquipmentType());
					this.game.pushGameController(new RecycleCardsGameController(this.game, this.player, Set.of(old)));
					this.game.pushGameController(new UnequipGameController(this.game, this.player, this.equipment.getEquipmentType()));
				}
				this.nextStage();
				break;
			case EQUIP:
				try {
					this.player.equip(this.equipment);
					this.equipment.onEquipped(this.game, this.player);
					this.nextStage();
				} catch (InvalidPlayerCommandException e) {
					e.printStackTrace();
				}
				break;
			case END:
				break;
		}
	}

	@Override
	protected EquipStage getInitialStage() {
		return EquipStage.UNEQUIP;
	}

}
