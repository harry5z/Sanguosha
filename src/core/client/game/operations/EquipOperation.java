package core.client.game.operations;

import cards.Card;
import cards.equipments.Equipment;
import commands.Command;
import commands.game.server.ingame.EquipInGameServerCommand;

public class EquipOperation extends AbstractCardUsageOperation {

	@Override
	protected Command<?> getCommand(Card card) {
		return new EquipInGameServerCommand((Equipment) card);
	}

}
