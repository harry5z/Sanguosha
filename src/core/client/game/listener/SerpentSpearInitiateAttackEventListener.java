package core.client.game.listener;

import java.util.Set;

import cards.equipments.Equipment.EquipmentType;
import core.client.GamePanel;
import core.client.game.event.EnableAttackClientGameEvent;
import core.client.game.operations.equipment.SerpentSpearInitiateAttackOperation;

public class SerpentSpearInitiateAttackEventListener extends AbstractClientEventListener<EnableAttackClientGameEvent> {
	
	@Override
	public Class<EnableAttackClientGameEvent> getEventClass() {
		return EnableAttackClientGameEvent.class;
	}
	
	@Override
	public void handle(EnableAttackClientGameEvent event, GamePanel panel) {
		if (event.isStart()) {
			if (panel.getGameState().getSelf().getAttackUsed() < panel.getGameState().getSelf().getAttackLimit()) {
				panel.getGameUI().getEquipmentRackUI().setActivatable(
					Set.of(EquipmentType.WEAPON),
					e -> panel.pushOperation(new SerpentSpearInitiateAttackOperation(e))
				);
			}
		} else {
			this.onDeactivated(panel);
		}
	}

	@Override
	public void onDeactivated(GamePanel panel) {
		panel.getGameUI().getEquipmentRackUI().setUnactivatable(Set.of(EquipmentType.WEAPON));
	}

}
