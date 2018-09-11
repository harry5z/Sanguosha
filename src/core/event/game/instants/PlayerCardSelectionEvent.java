package core.event.game.instants;

import java.util.Collection;

import cards.equipments.Equipment.EquipmentType;
import core.event.game.basic.AbstractSingleTargetGameEvent;
import core.player.PlayerCardZone;
import core.player.PlayerInfo;

public class PlayerCardSelectionEvent extends AbstractSingleTargetGameEvent {
	
	private PlayerInfo source;
	private Collection<PlayerCardZone> zones;
	private Collection<EquipmentType> equipmentTypes;

	public PlayerCardSelectionEvent(PlayerInfo source, PlayerInfo target, Collection<PlayerCardZone> zones) {
		this(source, target, zones, null);
	}
	
	public PlayerCardSelectionEvent(
		PlayerInfo source,
		PlayerInfo target,
		Collection<PlayerCardZone> zones,
		Collection<EquipmentType> equipmentTypes
	) {
		super(target);
		this.source = source;
		this.zones = zones;
		this.equipmentTypes = equipmentTypes;
	}
	
	
	public PlayerInfo getSource() {
		return this.source;
	}
	
	public Collection<PlayerCardZone> getZones() {
		return this.zones;
	}
	
	public Collection<EquipmentType> getEquipmentTypes() {
		return this.equipmentTypes;
	}

}
