package commands.game.client.sync.equipment;

import cards.equipments.Equipment.EquipmentType;
import commands.game.client.AbstractGameUIClientCommand;
import core.client.GamePanel;
import exceptions.server.game.InvalidPlayerCommandException;

public class SyncUnequipGameUIClientCommand extends AbstractGameUIClientCommand {

	private static final long serialVersionUID = -4975131296735057066L;

	private final String name;
	private final EquipmentType type;
	
	public SyncUnequipGameUIClientCommand(String name, EquipmentType type) {
		this.name = name;
		this.type = type;
	}
	
	@Override
	protected void execute(GamePanel panel) {
		try {
			if (panel.getGameState().getSelf().getName().equals(name)) {
				panel.getGameState().getSelf().unequip(type);
			} else {
				panel.getGameState().getPlayer(name).unequip(type);
			}
		} catch (InvalidPlayerCommandException e) {
			e.printStackTrace();
		}
	}

}
