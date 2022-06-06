package core.client.game.operations.mechanics;

import commands.server.ingame.EndStageInGameServerCommand;
import core.client.game.event.DealClientGameEvent;
import core.client.game.event.EnableAttackClientGameEvent;
import core.client.game.operations.AbstractOperation;
import ui.game.interfaces.CardUI;
import ui.game.interfaces.GameUI;

/**
 * One of the most important Operation in the game. DealOperation represents
 * the DEAL stage of a player. The player may use cards, skills, and can click
 * "END" to end their turn.
 * 
 * @author Harry
 *
 */
public class DealOperation extends AbstractOperation {

	/**
	 * 
	 * <p>{@inheritDoc}</p>
	 * 
	 * DealOperation should be the only Operation that sends a command
	 * when onEnded is called
	 * 
	 */
	@Override
	public void onEnded() {
		this.onUnloaded();
		this.onDeactivated();
		this.panel.sendResponse(new EndStageInGameServerCommand());
	}
	
	@Override
	public void onCardClicked(CardUI card) {
		panel.pushOperation(card.getCard().generateOperation(card));
	}
	
	@Override
	public void onLoaded() {
		GameUI panelUI = this.panel.getGameUI();
		// TODO Change card activatable to event based for scalability
		for(CardUI cardUI : panelUI.getCardRackUI().getCardUIs()) {
			if (cardUI.getCard().isActivatable(this.panel.getGameState())) {
				cardUI.setActivatable(true);
			}
		}
		panelUI.setMessage("It is your turn to deal");
		panelUI.setEndEnabled(true); // This should be the only class where setEndEnabled is called
		this.panel.emit(new EnableAttackClientGameEvent(true));
		this.panel.emit(new DealClientGameEvent(true));
	}
	
	
	
	@Override
	public void onUnloaded() {
		GameUI panelUI = panel.getGameUI();
		for(CardUI cardUI : panelUI.getCardRackUI().getCardUIs()) {
			cardUI.setActivatable(false);
		}
		panelUI.clearMessage();
		this.panel.emit(new EnableAttackClientGameEvent(false));
		this.panel.emit(new DealClientGameEvent(false));
	}
	
	/**
	 * 
	 * <p>{@inheritDoc}</p>
	 * 
	 * DealOperation needs to also disable the END button
	 */
	@Override
	public void onDeactivated() {
		this.panel.getGameUI().setEndEnabled(false);
		super.onDeactivated();
	}

}
