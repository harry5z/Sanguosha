package commands.game.client.sync.equipment;

import cards.equipments.Equipment;
import commands.game.client.AbstractGameUIClientCommand;
import core.client.GamePanel;
import core.heroes.Hero;
import exceptions.server.game.InvalidPlayerCommandException;

public class SyncEquipGameUIClientCommand extends AbstractGameUIClientCommand {

	private static final long serialVersionUID = -9130336753281283554L;

	private final String name;
	private final Equipment equipment;
	
	public SyncEquipGameUIClientCommand(String name, Equipment equipment) {
		this.name = name;
		this.equipment = equipment;
	}
	
	@Override
	protected void execute(GamePanel<? extends Hero> panel) {
		try {
			if (panel.getContent().getSelf().getName().equals(name)) {
				panel.getContent().getSelf().equip(equipment);
			} else {
				panel.getContent().getPlayer(name).equip(equipment);
			}
		} catch (InvalidPlayerCommandException e) {
			e.printStackTrace();
		}
	}

}
