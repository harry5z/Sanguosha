package core.event.game.instants;

import java.util.Collection;

import core.event.game.basic.AbstractSingleTargetGameEvent;
import core.player.PlayerCardZone;
import core.player.PlayerInfo;

public class PlayerCardSelectionEvent extends AbstractSingleTargetGameEvent {
	
	private PlayerInfo source;
	private Collection<PlayerCardZone> zones;

	public PlayerCardSelectionEvent(PlayerInfo source, PlayerInfo target, Collection<PlayerCardZone> zones) {
		super(target);
		this.source = source;
		this.zones = zones;
	}
	
	public PlayerInfo getSource() {
		return this.source;
	}
	
	public Collection<PlayerCardZone> getZones() {
		return this.zones;
	}

}
