package core.event.game;

import cards.equipments.Equipment;
import core.player.PlayerCompleteServer;

public class EquipEvent extends AbstractGameEvent {

	public final PlayerCompleteServer player;
	public final Equipment equipment;
	
	public EquipEvent(PlayerCompleteServer player, Equipment equipment) {
		this.player = player;
		this.equipment = equipment;
	}
}
