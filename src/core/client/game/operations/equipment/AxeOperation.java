package core.client.game.operations.equipment;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import cards.Card;
import cards.equipments.Equipment.EquipmentType;
import commands.game.server.ingame.AxeReactionInGameServerCommand;
import core.client.GamePanel;
import core.client.game.operations.Operation;
import core.player.PlayerCardZone;
import ui.game.interfaces.Activatable;
import ui.game.interfaces.CardUI;
import ui.game.interfaces.EquipmentUI;

public class AxeOperation implements Operation {
	
	private GamePanel panel;
	private final Map<Activatable, PlayerCardZone> cards;
	
	public AxeOperation() {
		this.cards = new LinkedHashMap<>();
	}
	
	@Override
	public void onEnded() {
		this.panel.getGameUI().clearMessage();
		for (CardUI cardUI : this.panel.getGameUI().getCardRackUI().getCardUIs()) {
			cardUI.setActivatable(false);
		}
		this.panel.getGameUI().getEquipmentRackUI().setActivatable(
			Set.of(EquipmentType.SHIELD, EquipmentType.HORSEPLUS, EquipmentType.HORSEMINUS),
			false
		);
		this.panel.getGameUI().getEquipmentRackUI().setActivated(
			Set.of(EquipmentType.SHIELD, EquipmentType.HORSEPLUS, EquipmentType.HORSEMINUS),
			false
		);
		this.panel.getGameUI().setCancelEnabled(false);
		this.panel.getGameUI().setConfirmEnabled(false);
	}
	
	@Override
	public void onCanceled() {
		this.onEnded();
		this.panel.getChannel().send(new AxeReactionInGameServerCommand(new HashMap<>()));
	}
	
	@Override
	public void onConfirmed() {
		this.onEnded();
		Map<Card, PlayerCardZone> map = this.cards.entrySet().stream().collect(Collectors.toMap(
			entry -> entry.getValue() == PlayerCardZone.HAND ? ((CardUI) entry.getKey()).getCard() : ((EquipmentUI) entry.getKey()).getEquipment(),
			entry -> entry.getValue()
		));
		this.panel.getChannel().send(new AxeReactionInGameServerCommand(map));
	}
	
	@Override
	public void onCardClicked(CardUI card) {
		if (this.cards.containsKey(card)) {
			this.cards.remove(card);
			card.setActivated(false);
		} else {
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
	
	@Override
	public void onEquipmentClicked(EquipmentUI equipment) {
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
	public void onActivated(GamePanel panel, Activatable source) {
		this.panel = panel;
		panel.getGameUI().setMessage("Axe: Discard 2 cards to make the Attack hit?");
		for (CardUI cardUI : panel.getGameUI().getCardRackUI().getCardUIs()) {
			cardUI.setActivatable(true);
		}
		panel.getGameUI().getEquipmentRackUI().setActivatable(Set.of(EquipmentType.SHIELD, EquipmentType.HORSEPLUS, EquipmentType.HORSEMINUS), true);
		panel.getGameUI().setCancelEnabled(true);
	}

}
