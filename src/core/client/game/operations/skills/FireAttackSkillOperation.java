package core.client.game.operations.skills;

import java.util.HashSet;
import java.util.Set;

import cards.Card.Color;
import core.client.GamePanel;
import core.client.game.operations.Operation;
import core.client.game.operations.instants.FireAttackOperation;
import ui.game.interfaces.Activatable;
import ui.game.interfaces.CardUI;
import ui.game.interfaces.SkillUI;

public class FireAttackSkillOperation implements Operation {
	
	private final SkillUI skill;
	private GamePanel panel;
	private Set<CardUI> selectableCards;
	private CardUI cardSelected = null;
	private Operation previousOperation;
	
	public FireAttackSkillOperation(SkillUI skill) {
		this.skill = skill;
		this.selectableCards = new HashSet<>();
		this.previousOperation = null;
	}

	@Override
	public void onActivated(GamePanel panel, Activatable source) {
		this.panel = panel;
		while (panel.getCurrentOperation() != null) {
			this.previousOperation = panel.getCurrentOperation();
			this.previousOperation.onDeactivated();
		}
		for (CardUI card : panel.getGameUI().getCardRackUI().getCardUIs()) {
			if (card.getCard().getColor() == Color.RED) {
				card.setActivatable(true);
				this.selectableCards.add(card);
			}
		}
		panel.getGameUI().setCancelEnabled(true);
		panel.getGameUI().setEndEnabled(true);
		panel.getGameUI().setMessage("Select a RED card to initiate Fire Attack");
	}
	
	@Override
	public void onEnded() {
		this.onDeactivated();
		this.panel.pushOperation(this.previousOperation, null);
		this.panel.getCurrentOperation().onEnded();
	}
	
	@Override
	public void onCanceled() {
		this.onDeactivated();
		this.panel.pushOperation(this.previousOperation, null);
	}
	
	@Override
	public void onCardClicked(CardUI card) {
		this.cardSelected = card;
		this.panel.pushOperation(new FireAttackOperation(), card);
	}
	
	@Override
	public void onSkillClicked(SkillUI skill) {
		this.onDeactivated();
		this.panel.pushOperation(this.previousOperation, null);
		this.panel.getCurrentOperation().onSkillClicked(skill);
	}
	
	@Override
	public void onDeactivated() {
		this.skill.setActivated(false);
		this.selectableCards.forEach(card -> card.setActivatable(false));
		this.panel.getGameUI().setConfirmEnabled(false);
		this.panel.getGameUI().clearMessage();
		this.panel.popOperation();
	}

}
