package core.client.game.listener;

import java.util.Set;

import cards.equipments.Equipment.EquipmentType;
import core.client.GamePanel;
import core.client.game.event.EnableAttackClientGameEvent;
import core.client.game.operations.equipment.SerpentSpearOperation;
import ui.game.interfaces.Activatable;

public class SerpentSpearInitiateAttackEventListener extends AbstractClientEventListener<EnableAttackClientGameEvent> {
	
	@Override
	public Class<EnableAttackClientGameEvent> getEventClass() {
		return EnableAttackClientGameEvent.class;
	}
	
	@Override
	public void handle(EnableAttackClientGameEvent event, GamePanel panel) {
		if (event.isStart()) {
			if (panel.getGameState().getSelf().getAttackUsed() < panel.getGameState().getSelf().getAttackLimit()) {
				panel.getGameUI().getEquipmentRackUI().setActivatable(Set.of(EquipmentType.WEAPON), true);
				panel.getGameUI().getEquipmentRackUI().registerOnActivatedListener(EquipmentType.WEAPON, (e) -> {
					panel.pushOperation(new SerpentSpearOperation((Activatable) e.getSource(), true));
				});
			}
		} else {
			this.onDeactivated(panel);
		}
	}

	@Override
	public void onDeactivated(GamePanel panel) {
		panel.getGameUI().getEquipmentRackUI().setActivatable(Set.of(EquipmentType.WEAPON), false);
		panel.getGameUI().getEquipmentRackUI().removeOnActivatedListeners(EquipmentType.WEAPON);		
	}

}
