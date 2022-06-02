package commands.game.client;

import java.util.Collection;
import java.util.Set;

import cards.equipments.Equipment.EquipmentType;
import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.PlayerCardSelectionInGameServerCommand;
import core.client.game.operations.Operation;
import core.client.game.operations.mechanics.PlayerCardSelectionOperation;
import core.player.PlayerCardZone;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;

public class ShowCardSelectionPanelUIClientCommand extends AbstractSingleTargetOperationGameClientCommand {

	private static final long serialVersionUID = 1L;
	
	private final transient PlayerCompleteServer target;
	private final PlayerInfo selectionTarget;
	private final Collection<PlayerCardZone> zones;
	private final Collection<EquipmentType> equipmentTypes;
	
	public ShowCardSelectionPanelUIClientCommand(
		PlayerInfo source,
		PlayerCompleteServer selectionTarget,
		Collection<PlayerCardZone> zones,
		Collection<EquipmentType> equipmentTypes
	) {
		super(source);
		this.target = selectionTarget;
		this.selectionTarget = selectionTarget.getPlayerInfo();
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

	@Override
	public InGameServerCommand getDefaultResponse() {
		// First try to select a card on hand
		if (zones.contains(PlayerCardZone.HAND) && target.getHandCount() > 0) {
			return new PlayerCardSelectionInGameServerCommand(target.getCardsOnHand().get(0), PlayerCardZone.HAND);
		}
		// Second try to select an equipment, in the order of Weapon > Shield > Horse Plus > Horse Minus
		if (zones.contains(PlayerCardZone.EQUIPMENT) && target.isEquipped()) {
			if (target.isEquipped(EquipmentType.WEAPON)) {
				return new PlayerCardSelectionInGameServerCommand(target.getWeapon(), PlayerCardZone.EQUIPMENT);
			}
			if (target.isEquipped(EquipmentType.SHIELD)) {
				return new PlayerCardSelectionInGameServerCommand(target.getWeapon(), PlayerCardZone.EQUIPMENT);
			}
			if (target.isEquipped(EquipmentType.HORSEPLUS)) {
				return new PlayerCardSelectionInGameServerCommand(target.getWeapon(), PlayerCardZone.EQUIPMENT);
			}
			if (target.isEquipped(EquipmentType.HORSEMINUS)) {
				return new PlayerCardSelectionInGameServerCommand(target.getWeapon(), PlayerCardZone.EQUIPMENT);
			}
		}
		// Lastly try to select a Delayed Special card
		if (zones.contains(PlayerCardZone.DELAYED) && target.getDelayedQueue().size() > 0) {
			return new PlayerCardSelectionInGameServerCommand(target.getDelayedQueue().peek().delayed, PlayerCardZone.DELAYED);
		}
		// by design we should not reach here
		throw new RuntimeException("Target has no valid card to select");
	}
	
	@Override
	protected String getMessageForOthers() {
		return "Waiting on " + selectionTarget.getName() + " to select a card";
	}

}
