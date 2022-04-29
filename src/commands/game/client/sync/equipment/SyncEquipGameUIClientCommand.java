package commands.game.client.sync.equipment;

import cards.equipments.Equipment;
import commands.game.client.AbstractGameUIClientCommand;
import core.client.GamePanel;
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
	protected void execute(GamePanel panel) {
		try {
			if (panel.getGameState().getSelf().getName().equals(name)) {
				panel.getGameState().getSelf().equip(equipment);
			} else {
				panel.getGameState().getPlayer(name).equip(equipment);
			}
		} catch (InvalidPlayerCommandException e) {
			e.printStackTrace();
		}
	}

}
