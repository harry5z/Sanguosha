package core.client.game.listener;

import java.util.Set;

import cards.equipments.Equipment.EquipmentType;
import core.client.GamePanel;
import core.client.game.event.EnableAttackClientGameEvent;
import core.client.game.operations.equipment.SerpentSpearOperation;
import core.heroes.Hero;
import ui.game.interfaces.Activatable;

public class SerpentSpearInitiateAttackEventListener extends AbstractClientEventListener<EnableAttackClientGameEvent> {
	
	@Override
	public Class<EnableAttackClientGameEvent> getEventClass() {
		return EnableAttackClientGameEvent.class;
	}
	
	@Override
	public void handle(EnableAttackClientGameEvent event, GamePanel<Hero> panel) {
		if (event.isStart()) {
			if (panel.getContent().getSelf().getAttackUsed() < panel.getContent().getSelf().getAttackLimit()) {
				panel.getContent().getEquipmentRackUI().setActivatable(Set.of(EquipmentType.WEAPON), true);
				panel.getContent().getEquipmentRackUI().registerOnActivatedListener(EquipmentType.WEAPON, (e) -> {
					panel.pushOperation(new SerpentSpearOperation(true), (Activatable) e.getSource());
				});
			}
		} else {
			this.onDeactivated(panel);
		}
	}

	@Override
	public void onDeactivated(GamePanel<Hero> panel) {
		panel.getContent().getEquipmentRackUI().setActivatable(Set.of(EquipmentType.WEAPON), false);
		panel.getContent().getEquipmentRackUI().removeOnActivatedListeners(EquipmentType.WEAPON);		
	}

}
