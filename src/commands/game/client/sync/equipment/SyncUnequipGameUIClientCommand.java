package commands.game.client.sync.equipment;

import cards.equipments.Equipment.EquipmentType;
import commands.game.client.GeneralGameUIClientCommand;
import core.client.GamePanel;
import core.heroes.Hero;
import exceptions.server.game.InvalidPlayerCommandException;

public class SyncUnequipGameUIClientCommand extends GeneralGameUIClientCommand {

	private static final long serialVersionUID = -4975131296735057066L;

	private final String name;
	private final EquipmentType type;
	
	public SyncUnequipGameUIClientCommand(String name, EquipmentType type) {
		this.name = name;
		this.type = type;
	}
	
	@Override
	protected void execute(GamePanel<? extends Hero> panel) {
		try {
			if (panel.getContent().getSelf().getName().equals(name)) {
				panel.getContent().getSelf().unequip(type);
			} else {
				panel.getContent().getPlayer(name).unequip(type);
			}
		} catch (InvalidPlayerCommandException e) {
			e.printStackTrace();
		}
	}

}
