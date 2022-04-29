package core.client.game.operations;

import cards.Card;
import cards.equipments.Equipment;
import commands.game.server.ingame.EquipInGameServerCommand;
import commands.game.server.ingame.InGameServerCommand;
import ui.game.interfaces.Activatable;

public class EquipOperation extends AbstractCardUsageOperation {

	public EquipOperation(Activatable source) {
		super(source);
	}

	@Override
	protected InGameServerCommand getCommand(Card card) {
		return new EquipInGameServerCommand((Equipment) card);
	}

}
