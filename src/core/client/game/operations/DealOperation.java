package core.client.game.operations;

import commands.game.server.ingame.EndStageInGameServerCommand;
import core.client.GamePanel;
import core.client.game.event.DealClientGameEvent;
import core.client.game.event.EnableAttackClientGameEvent;
import ui.game.interfaces.CardUI;
import ui.game.interfaces.EquipmentUI;
import ui.game.interfaces.GameUI;

public class DealOperation implements Operation {

	private GamePanel panel;
	
	@Override
	public void onEnded() {
		this.onDeactivated();
		// resetting weapon/skills, etc.
		panel.getChannel().send(new EndStageInGameServerCommand());
	}
	
	/**
	 * <p>{@inheritDoc}</p>
	 * 
	 * <p>
	 * Deal itself will not enable confirm button,
	 * but other operations may, so this is a way to
	 * clean up the UI changes made by Deal operation
	 * </p>
	 */
	@Override
	public void onConfirmed() {
		this.onDeactivated();
	}
	
	@Override
	public void onCardClicked(CardUI card) {
		panel.pushOperation(card.getCard().generateOperation(card));
	}
	
	@Override
	public void onEquipmentClicked(EquipmentUI equipment) {
		panel.pushOperation(equipment.getEquipment().generateOperation(equipment));
	}

	@Override
	public void onActivated(GamePanel panel) {
		this.panel = panel;
		GameUI panelUI = panel.getGameUI();
		
		// TODO Change card activatable to event based for scalability
		for(CardUI cardUI : panelUI.getCardRackUI().getCardUIs()) {
			if (cardUI.getCard().isActivatable(panel.getGameState())) {
				cardUI.setActivatable(true);
			}
		}
		panelUI.setEndEnabled(true);
		this.panel.emit(new EnableAttackClientGameEvent(true));
		// if (player.getWeapon()...) check weapon use
		this.panel.emit(new DealClientGameEvent(true));
	}
	
	@Override
	public void onDeactivated() {
		GameUI panelUI = panel.getGameUI();
		panelUI.setEndEnabled(false);
		for(CardUI cardUI : panelUI.getCardRackUI().getCardUIs()) {
			cardUI.setActivatable(false);
		}
		this.panel.emit(new EnableAttackClientGameEvent(false));
		this.panel.emit(new DealClientGameEvent(false));
		this.panel.popOperation();
	}

}
