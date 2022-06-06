package commands.server.ingame;

import cards.equipments.Equipment;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractSingleStageGameController;
import core.server.game.controllers.GameController;
import core.server.game.controllers.mechanics.EquipGameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.IllegalPlayerActionException;
import exceptions.server.game.InvalidCardException;

public class EquipInGameServerCommand extends InGameServerCommand {
	
	private static final long serialVersionUID = 7476795400557013712L;

	private Equipment equipment;

	/**
	 * 
	 * @param card : equipment to be equipped, cannot be null
	 */
	public EquipInGameServerCommand(Equipment card) {
		this.equipment = card;
	}

	@Override
	public GameController getGameController() {
		return new AbstractSingleStageGameController() {
			
			@Override
			protected void handleOnce(GameInternal game) throws GameFlowInterruptedException {
				game.pushGameController(new EquipGameController(game.getCurrentPlayer(), equipment));
			}
		};
	}

	@Override
	public void validate(GameInternal game) throws IllegalPlayerActionException {
		if (equipment == null) {
			throw new IllegalPlayerActionException("Equip: Equipment cannot be null");
		}
		try {
			equipment = (Equipment) game.getDeck().getValidatedCard(equipment);
		} catch (InvalidCardException e) {
			throw new IllegalPlayerActionException("Equip: Card is invalid");
		} catch (ClassCastException e) {
			throw new IllegalPlayerActionException("Equip: Card is not an Equipment");
		}
		if (!source.getCardsOnHand().contains(equipment)) {
			throw new IllegalPlayerActionException("Equipment: Player does not own the card used");
		}			
	}

}
