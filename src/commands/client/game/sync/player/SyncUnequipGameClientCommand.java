package commands.client.game.sync.player;

import cards.equipments.Equipment.EquipmentType;
import core.GameState;
import exceptions.server.game.InvalidPlayerCommandException;

public class SyncUnequipGameClientCommand extends AbstractSyncPlayerClientCommand {

	private static final long serialVersionUID = -4975131296735057066L;

	private final String name;
	private final EquipmentType type;
	
	public SyncUnequipGameClientCommand(String name, EquipmentType type) {
		this.name = name;
		this.type = type;
	}
	
	@Override
	protected void sync(GameState state) throws InvalidPlayerCommandException {
		if (state.getSelf().getName().equals(name)) {
			state.getSelf().unequip(type);
		} else {
			state.getPlayer(name).unequip(type);
		}
	}

}
