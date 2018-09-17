package core.client.game.listener;

import java.util.Set;

import cards.equipments.Equipment.EquipmentType;
import core.client.GamePanel;
import core.client.game.event.AttackReactionClientGameEvent;
import core.client.game.operations.equipment.SerpentSpearOperation;
import core.heroes.Hero;
import ui.game.interfaces.Activatable;

public class SerpentSpearAttackReactionEventListener extends AbstractClientEventListener<AttackReactionClientGameEvent> {

	@Override
	public Class<AttackReactionClientGameEvent> getEventClass() {
		return AttackReactionClientGameEvent.class;
	}

	@Override
	public void handle(AttackReactionClientGameEvent event, GamePanel<Hero> panel) {
		if (event.isStart()) {
			panel.getContent().getEquipmentRackUI().setActivatable(Set.of(EquipmentType.WEAPON), true);
			panel.getContent().getEquipmentRackUI().registerOnActivatedListener(EquipmentType.WEAPON, (e) -> {
				panel.pushOperation(new SerpentSpearOperation(false), (Activatable) e.getSource());
			});
		} else {
			panel.getContent().getEquipmentRackUI().setActivatable(Set.of(EquipmentType.WEAPON), false);
			panel.getContent().getEquipmentRackUI().removeOnActivatedListeners(EquipmentType.WEAPON);
		}
	}

}
