package commands.game.client.sync.equipment;

import cards.equipments.Equipment;
import cards.equipments.Equipment.EquipmentType;
import commands.game.client.AbstractGameUIClientCommand;
import core.client.GamePanel;
import core.heroes.Hero;
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
	protected void execute(GamePanel<? extends Hero> panel) {
		try {
			if (panel.getContent().getSelf().getName().equals(name)) {
				Equipment equipment = panel.getContent().getSelf().getEquipment(type);
				panel.getContent().getSelf().unequip(type);
				// TODO: Fix GamePanel generic type
				equipment.onUnequipped((GamePanel<Hero>) panel);
				
			} else {
				panel.getContent().getPlayer(name).unequip(type);
			}
		} catch (InvalidPlayerCommandException e) {
			e.printStackTrace();
		}
	}

}
