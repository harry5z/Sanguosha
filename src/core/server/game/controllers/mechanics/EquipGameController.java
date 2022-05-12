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
	
	private final PlayerCompleteServer source;
	private final Equipment equipment;
	
	public EquipGameController(PlayerCompleteServer player, Equipment equipment) {
		this.source = player;
		this.equipment = equipment;
	}
	
	@Override
	protected void handleStage(Game game, EquipStage stage) throws GameFlowInterruptedException {
		PlayerCompleteServer player = this.source != null ? this.source : game.getCurrentPlayer();
		switch (stage) {
			case UNEQUIP:
				try {
					// TODO: convert to controller
					player.removeCardFromHand(this.equipment);
				} catch (InvalidPlayerCommandException e) {
					
				}
				
				// discard old equipment (if any) at the same slot
				if (player.isEquipped(this.equipment.getEquipmentType())) {
					Equipment old  = player.getEquipment(this.equipment.getEquipmentType());
					game.pushGameController(new RecycleCardsGameController(player, Set.of(old)));
					game.pushGameController(new UnequipGameController(player, this.equipment.getEquipmentType()));
				}
				this.nextStage();
				break;
			case EQUIP:
				try {
					player.equip(this.equipment);
					this.equipment.onEquipped(game, player);
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
