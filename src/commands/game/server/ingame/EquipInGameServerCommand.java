package commands.game.server.ingame;

import cards.equipments.Equipment;
import core.server.game.Game;
import core.server.game.controllers.AbstractSingleStageGameController;
import core.server.game.controllers.GameController;
import core.server.game.controllers.mechanics.EquipGameController;
import exceptions.server.game.GameFlowInterruptedException;

public class EquipInGameServerCommand extends InGameServerCommand {
	
	private static final long serialVersionUID = 7476795400557013712L;

	private final Equipment equipment;

	public EquipInGameServerCommand(Equipment card) {
		this.equipment = card;
	}

	@Override
	protected GameController getGameController(Game game) {
		return new AbstractSingleStageGameController(game) {
			
			@Override
			protected void handleOnce() throws GameFlowInterruptedException {
				game.pushGameController(new EquipGameController(game, game.getCurrentPlayer(), equipment));
			}
		};
	}

}
