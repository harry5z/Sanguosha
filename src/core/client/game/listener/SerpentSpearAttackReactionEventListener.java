package core.client.game.listener;

import java.util.Set;

import cards.equipments.Equipment.EquipmentType;
import core.client.GamePanel;
import core.client.game.event.AttackReactionClientGameEvent;
import core.client.game.operations.equipment.SerpentSpearAttackReactionOperation;

public class SerpentSpearAttackReactionEventListener extends AbstractClientEventListener<AttackReactionClientGameEvent> {

	@Override
	public Class<AttackReactionClientGameEvent> getEventClass() {
		return AttackReactionClientGameEvent.class;
	}

	@Override
	public void handle(AttackReactionClientGameEvent event, GamePanel panel) {
		if (event.isStart()) {
			panel.getGameUI().getEquipmentRackUI().setActivatable(
				Set.of(EquipmentType.WEAPON),
				e -> panel.pushOperation(new SerpentSpearAttackReactionOperation(e))
			);
		} else {
			this.onDeactivated(panel);
		}
	}

	@Override
	public void onDeactivated(GamePanel panel) {
		panel.getGameUI().getEquipmentRackUI().setUnactivatable(Set.of(EquipmentType.WEAPON));
	}

}
