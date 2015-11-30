package core.client.game.operations;

import commands.game.server.ingame.EndStageInGameServerCommand;
import net.client.GamePanel;
import ui.game.Activatable;
import ui.game.CardGui;
import ui.game.EquipmentGui;
import ui.game.GamePanelUI;

public class DealOperation implements Operation {

	private GamePanel panel;
	
	@Override
	public void onEnded() {
		onConfirmed();
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
		GamePanelUI panelUI = panel.getContent();
		panelUI.setEndEnabled(false);
		for(CardGui cardUI : panelUI.getCardRackUI().getCardUIs()) {
			cardUI.setActivatable(false);
		}
	}
	
	@Override
	public void onCardClicked(CardGui card) {
		panel.pushOperation(card.getCard().generateOperation(), card);
	}
	
	@Override
	public void onEquipmentClicked(EquipmentGui equipment) {
		panel.pushOperation(equipment.getEquipment().generateOperation(), equipment);
	}

	@Override
	public void onActivated(GamePanel panel, Activatable source) {
		this.panel = panel;
		GamePanelUI panelUI = panel.getContent();
		panelUI.showCountdownBar();
		for(CardGui cardUI : panelUI.getCardRackUI().getCardUIs()) {
			if (cardUI.getCard().isActivatable(panelUI)) {
				cardUI.setActivatable(true);
			}
		}
		panelUI.setEndEnabled(true);
		// if (player.getWeapon()...) check weapon use
		// skills
	}

}
