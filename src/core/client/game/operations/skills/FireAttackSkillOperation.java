package core.client.game.operations.skills;

import cards.Card.Color;
import core.client.game.operations.AbstractOperation;
import core.client.game.operations.instants.FireAttackOperation;
import ui.game.interfaces.CardUI;
import ui.game.interfaces.PlayerUI;
import ui.game.interfaces.SkillUI;

public class FireAttackSkillOperation extends AbstractOperation {
	
	private final SkillUI skill;
	private CardUI cardSelected;
	private FireAttackOperation op;
	
	public FireAttackSkillOperation(SkillUI skill) {
		this.skill = skill;
		this.cardSelected = null;
		this.op = null;
	}
	
	@Override
	public void onConfirmed() {
		this.onUnloaded();
		this.onDeactivated();
		this.op.onConfirmed();
	}
	
	@Override
	public void onCanceled() {
		if (this.op != null) {
			this.op.onUnloaded();
		}
		super.onCanceled();
	}
	
	@Override
	public void onEnded() {
		if (this.op != null) {
			this.op.onUnloaded();
		}
		super.onEnded();
	}
	
	@Override
	public void onPlayerClicked(PlayerUI player) {
		this.op.onPlayerClicked(player);
	}

	@Override
	public void onCardClicked(CardUI card) {
		if (this.cardSelected == null) { // select a card for Fire Attack
			this.cardSelected = card;
			this.onCardSelectionUnloaded();
			this.op = new FireAttackOperation(card);
			this.op.onActivated(this.panel);
		} else { // cancels card selection
			this.op.onUnloaded();
			this.op = null;
			this.cardSelected = null;
			this.onCardSelectionLoaded();
		}
	}
	
	@Override
	public void onLoaded() {
		this.skill.setActivatable(true);
		this.skill.setActivated(true);
		this.skill.setActionOnActivation(() -> {
			// By implementation, this has to be the Fire Attack itself
			// Behave as if CANCEL is clicked
			this.onCanceled();
		});
		this.onCardSelectionLoaded();
	}
	
	@Override
	public void onUnloaded() {
		this.skill.setActivatable(false);
		this.skill.setActivated(false);
		this.onCardSelectionUnloaded();
	}
	
	private void onCardSelectionLoaded() {
		for (CardUI card : this.panel.getGameUI().getCardRackUI().getCardUIs()) {
			if (card.getCard().getColor() == Color.RED) {
				card.setActivatable(true);
			}
		}
		this.panel.getGameUI().setCancelEnabled(true);
		this.panel.getGameUI().setMessage("Select a RED card to initiate Fire Attack");
	}
	
	private void onCardSelectionUnloaded() {
		this.panel.getGameUI().getCardRackUI().getCardUIs().forEach(card -> card.setActivatable(false));
		this.panel.getGameUI().setCancelEnabled(false);
		this.panel.getGameUI().clearMessage();
	}

}
