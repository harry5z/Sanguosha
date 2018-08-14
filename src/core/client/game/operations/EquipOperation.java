package core.client.game.operations;

import cards.Card;
import cards.equipments.Equipment;
import commands.game.server.ingame.EquipInGameServerCommand;
import commands.game.server.ingame.InGameServerCommand;

public class EquipOperation extends AbstractCardUsageOperation {

	@Override
	protected InGameServerCommand getCommand(Card card) {
		return new EquipInGameServerCommand((Equipment) card);
	}

}
