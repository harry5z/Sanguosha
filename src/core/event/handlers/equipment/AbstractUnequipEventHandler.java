package core.event.handlers.equipment;

import cards.equipments.Equipment.EquipmentType;
import core.event.game.UnequipEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;

public abstract class AbstractUnequipEventHandler extends AbstractEventHandler<UnequipEvent> {
	
	protected final EquipmentType type;

	public AbstractUnequipEventHandler(PlayerCompleteServer player, EquipmentType type) {
		super(player);
		this.type = type;
	}

	@Override
	public Class<UnequipEvent> getEventClass() {
		return UnequipEvent.class;
	}
}
