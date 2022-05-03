package core.client.game.operations.equipment;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import cards.Card;
import cards.equipments.Equipment.EquipmentType;
import commands.game.server.ingame.AxeReactionInGameServerCommand;
import core.client.game.operations.AbstractOperation;
import core.player.PlayerCardZone;
import ui.game.interfaces.Activatable;
import ui.game.interfaces.CardUI;
import ui.game.interfaces.EquipmentUI;

public class AxeOperation extends AbstractOperation {
	
	private final Map<Activatable, PlayerCardZone> cards;
	
	public AxeOperation() {
		this.cards = new LinkedHashMap<>();
	}
	
	@Override
	public void onCanceled() {
		this.onUnloaded();
		this.onDeactivated();
		this.panel.getChannel().send(new AxeReactionInGameServerCommand(new HashMap<>()));
	}
	
	@Override
	public void onConfirmed() {
		this.onUnloaded();
		this.onDeactivated();
		Map<Card, PlayerCardZone> map = this.cards.entrySet().stream().collect(Collectors.toMap(
			entry -> entry.getValue() == PlayerCardZone.HAND ? ((CardUI) entry.getKey()).getCard() : ((EquipmentUI) entry.getKey()).getEquipment(),
			entry -> entry.getValue()
		));
		this.panel.getChannel().send(new AxeReactionInGameServerCommand(map));
	}
	
	@Override
	public void onCardClicked(CardUI card) {
		if (this.cards.containsKey(card)) { // unselect a card
			this.cards.remove(card);
			card.setActivated(false);
		} else { // selecting a new card
			if (this.cards.size() == 2) {
				Activatable oldest = this.cards.keySet().iterator().next();
				this.cards.remove(oldest);
				oldest.setActivated(false);
			}
			this.cards.put(card, PlayerCardZone.HAND);
			card.setActivated(true);
		}
		this.panel.getGameUI().setConfirmEnabled(this.cards.size() == 2);
	}
	
	private void onEquipmentClicked(EquipmentUI equipment) {
		if (this.cards.containsKey(equipment)) {
			this.cards.remove(equipment);
			equipment.setActivated(false);
		} else {
			if (this.cards.size() == 2) {
				Activatable oldest = this.cards.keySet().iterator().next();
				this.cards.remove(oldest);
				oldest.setActivated(false);
			}
			this.cards.put(equipment, PlayerCardZone.EQUIPMENT);
			equipment.setActivated(true);
		}
		this.panel.getGameUI().setConfirmEnabled(this.cards.size() == 2);
}

	@Override
	public void onLoaded() {
		this.panel.getGameUI().setMessage("Axe: Discard 2 cards to make the Attack hit?");
		this.panel.getGameUI().getCardRackUI().getCardUIs().forEach(ui -> ui.setActivatable(true));
		this.panel.getGameUI().getEquipmentRackUI().setActivatable(
			Set.of(EquipmentType.SHIELD, EquipmentType.HORSEPLUS, EquipmentType.HORSEMINUS),
			e -> this.onEquipmentClicked(e)
		);
		this.panel.getGameUI().setCancelEnabled(true);
	}

	@Override
	public void onUnloaded() {
		this.panel.getGameUI().clearMessage();
		this.panel.getGameUI().getCardRackUI().getCardUIs().forEach(ui -> ui.setActivatable(false));
		this.panel.getGameUI().getEquipmentRackUI().setUnactivatable(
			Set.of(EquipmentType.SHIELD, EquipmentType.HORSEPLUS, EquipmentType.HORSEMINUS)		
		);
		this.panel.getGameUI().getEquipmentRackUI().setActivated(
			Set.of(EquipmentType.SHIELD, EquipmentType.HORSEPLUS, EquipmentType.HORSEMINUS),
			false
		);
		this.panel.getGameUI().setCancelEnabled(false);
		this.panel.getGameUI().setConfirmEnabled(false);		
	}
	
	

}
