package commands.game.client;

import java.util.Collection;
import java.util.Set;

import cards.equipments.Equipment.EquipmentType;
import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.PlayerCardSelectionInGameServerCommand;
import core.client.game.operations.Operation;
import core.client.game.operations.mechanics.PlayerCardSelectionOperation;
import core.player.PlayerCardZone;
import core.player.PlayerInfo;

public class ShowCardSelectionPanelUIClientCommand extends AbstractSingleTargetOperationGameClientCommand {

	private static final long serialVersionUID = 1L;
	
	private final PlayerInfo selectionTarget;
	private final Collection<PlayerCardZone> zones;
	private final Collection<EquipmentType> equipmentTypes;
	
	public ShowCardSelectionPanelUIClientCommand(
		PlayerInfo source,
		PlayerInfo selectionTarget,
		Collection<PlayerCardZone> zones,
		Collection<EquipmentType> equipmentTypes
	) {
		super(source);
		this.selectionTarget = selectionTarget;
		this.zones = zones;
		this.equipmentTypes = equipmentTypes;
	}

	@Override
	protected Operation getOperation() {
		return new PlayerCardSelectionOperation(this.selectionTarget, this.zones, this.equipmentTypes);
	}

	@Override
	public Set<Class<? extends InGameServerCommand>> getAllowedResponseTypes() {
		return Set.of(PlayerCardSelectionInGameServerCommand.class);
	}

}
