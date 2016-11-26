package commands.game.server.ingame;

import cards.equipments.Equipment;
import core.server.game.Game;
import core.server.game.controllers.interfaces.EquipmentUsableGameController;

public class EquipInGameServerCommand extends InGameServerCommand {
	
	private static final long serialVersionUID = 7476795400557013712L;

	private final Equipment equipment;

	public EquipInGameServerCommand(Equipment card) {
		this.equipment = card;
	}

	@Override
	public void execute(Game game) {
		game.<EquipmentUsableGameController>getGameController().onEquipped(equipment);
	}

}
