package core.client.game.operations.machanics;

import cards.equipments.Equipment;
import commands.game.server.ingame.EquipInGameServerCommand;
import commands.game.server.ingame.InGameServerCommand;
import core.client.game.operations.AbstractCardInitiatedNoTargetOperation;
import ui.game.interfaces.Activatable;

public class EquipOperation extends AbstractCardInitiatedNoTargetOperation {

	public EquipOperation(Activatable source) {
		super(source);
	}

	@Override
	protected InGameServerCommand getCommandOnConfirm() {
		return new EquipInGameServerCommand((Equipment) this.activator.getCard());
	}

	@Override
	protected String getMessage() {
		return "Equip " + this.activator.getCard().getName() + "?";
	}

}
