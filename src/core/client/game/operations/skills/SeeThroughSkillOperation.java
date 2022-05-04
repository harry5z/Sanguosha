package core.client.game.operations.skills;

import cards.Card.Color;
import commands.game.server.ingame.NeutralizationReactionInGameServerCommand;
import core.client.game.operations.AbstractOperation;
import ui.game.interfaces.CardUI;
import ui.game.interfaces.SkillUI;

public class SeeThroughSkillOperation extends AbstractOperation {
	
	private final SkillUI skill;
	private CardUI cardSelected;
	
	public SeeThroughSkillOperation(SkillUI skill) {
		this.skill = skill;
		this.cardSelected = null;
	}
	
	@Override
	public void onConfirmed() {
		this.onUnloaded();
		this.onDeactivated();
		this.panel.getChannel().send(
			new NeutralizationReactionInGameServerCommand(
				this.panel.getGameState().getSelf().getPlayerInfo(), 
				this.cardSelected.getCard()
			)
		);
	}
	
	@Override
	public void onCardClicked(CardUI card) {
		if (this.cardSelected == null) { // select a card for Neutralization
			this.cardSelected = card;
			this.cardSelected.setActivated(true);
			this.panel.getGameUI().getCardRackUI().getCardUIs().forEach(ui -> {
				if (ui != card) {
					ui.setActivatable(false);
				}
			});
			this.panel.getGameUI().setConfirmEnabled(true);
		} else { // cancels card selection
			this.cardSelected.setActivated(false);
			this.cardSelected = null;
			this.panel.getGameUI().getCardRackUI().getCardUIs().forEach(ui -> {
				if (ui.getCard().getColor() == Color.BLACK) {
					ui.setActivatable(true);
				}
			});
			this.panel.getGameUI().setConfirmEnabled(false);
		}
	}
	
	@Override
	public void onLoaded() {
		this.skill.setActivatable(true);
		this.skill.setActivated(true);
		this.skill.setActionOnActivation(() -> {
			// By implementation, this has to be the See Through skill itself
			// Behave as if CANCEL is clicked
			this.onCanceled();
		});
		this.panel.getGameUI().getCardRackUI().getCardUIs().forEach(ui -> {
			if (ui.getCard().getColor() == Color.BLACK) {
				ui.setActivatable(true);
			}
		});
		this.panel.getGameUI().setCancelEnabled(true);
		this.panel.getGameUI().setMessage("Select a BLACK card as Neutralization");
	}

	@Override
	public void onUnloaded() {
		this.skill.setActivatable(false);
		this.skill.setActivated(false);
		if (this.cardSelected != null) {
			this.cardSelected.setActivated(false);
		}
		this.panel.getGameUI().getCardRackUI().getCardUIs().forEach(ui -> {
			ui.setActivatable(false);
		});
		this.panel.getGameUI().setCancelEnabled(false);
		this.panel.getGameUI().setConfirmEnabled(false);
		this.panel.getGameUI().clearMessage();
	}

}
