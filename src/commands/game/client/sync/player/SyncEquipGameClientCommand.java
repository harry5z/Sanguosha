package commands.game.client.sync.player;

import cards.equipments.Equipment;
import core.GameState;
import exceptions.server.game.InvalidPlayerCommandException;

public class SyncEquipGameClientCommand extends AbstractSyncPlayerClientCommand {

	private static final long serialVersionUID = -9130336753281283554L;

	private final String name;
	private final Equipment equipment;
	
	public SyncEquipGameClientCommand(String name, Equipment equipment) {
		this.name = name;
		this.equipment = equipment;
	}
	
	@Override
	protected void sync(GameState state) throws InvalidPlayerCommandException {
		if (state.getSelf().getName().equals(name)) {
			state.getSelf().equip(equipment);
		} else {
			state.getPlayer(name).equip(equipment);
		}
	}

}
