package core.client.game.listener.equipment;

import java.util.Set;

import cards.equipments.Equipment.EquipmentType;
import core.client.GamePanel;
import core.client.game.event.AttackReactionClientGameEvent;
import core.client.game.listener.AbstractClientEventListener;
import core.client.game.operations.equipment.SerpentSpearAttackReactionOperation;

public class SerpentSpearAttackReactionEventListener extends AbstractClientEventListener<AttackReactionClientGameEvent> {

	@Override
	public Class<AttackReactionClientGameEvent> getEventClass() {
		return AttackReactionClientGameEvent.class;
	}

	@Override
	public void handleOnLoaded(AttackReactionClientGameEvent event, GamePanel panel) {
		panel.getGameUI().getEquipmentRackUI().setActivatable(
			Set.of(EquipmentType.WEAPON),
			e -> panel.pushOperation(new SerpentSpearAttackReactionOperation(e))
		);		
	}

	@Override
	public void handleOnUnloaded(GamePanel panel) {
		panel.getGameUI().getEquipmentRackUI().setUnactivatable(Set.of(EquipmentType.WEAPON));
	}

}
