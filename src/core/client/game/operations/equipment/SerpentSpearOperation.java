package core.client.game.operations.equipment;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

import commands.game.server.ingame.SerpentSpearAttackReactionInGameServerCommand;
import commands.game.server.ingame.SerpentSpearInitiateAttackInGameServerCommand;
import core.client.GamePanel;
import core.client.game.operations.Operation;
import core.heroes.Hero;
import ui.game.interfaces.Activatable;
import ui.game.interfaces.CardUI;
import ui.game.interfaces.PlayerUI;

public class SerpentSpearOperation implements Operation {
	
	private GamePanel<? extends Hero> panel;
	private Activatable source;
	private final boolean withTarget;
	private PlayerUI target;
	private Queue<CardUI> cards;
	private Operation previousOperation;
	
	public SerpentSpearOperation(boolean withTarget) {
		this.withTarget = withTarget;
		this.target = null;
		this.cards = new LinkedList<>();
		this.previousOperation = null;
	}
	
	@Override
	public void onCardClicked(CardUI card) {
		if (this.cards.contains(card)) {
			card.setActivated(false);
			this.cards.remove(card);
			this.panel.getContent().setConfirmEnabled(false);
			return;
		}
		
		if (this.cards.size() == 2) {
			CardUI first = this.cards.poll();
			first.setActivated(false);
		}
		this.cards.add(card);
		card.setActivated(true);
		
		if (this.cards.size() == 2) {
			if (!this.withTarget || (this.withTarget && this.target != null)) {
				this.panel.getContent().setConfirmEnabled(true);
			}
		}
	}
	
	@Override
	public void onPlayerClicked(PlayerUI player) {
		if (this.target == player) {
			this.target.setActivated(false);
			this.target = null;
			this.panel.getContent().setConfirmEnabled(false);
		} else if (this.target != null) {
			this.target.setActivated(false);
			this.target = player;
			this.target.setActivated(true);
		} else {
			this.target = player;
			this.target.setActivated(true);
			if (this.cards.size() == 2) {
				this.panel.getContent().setConfirmEnabled(true);
			}
		}
	}
	
	@Override
	public void onConfirmed() {
		this.onDeactivated();
		if (this.withTarget) {
			this.panel.getChannel().send(new SerpentSpearInitiateAttackInGameServerCommand(
				this.panel.getContent().getSelf().getPlayerInfo(),
				this.target.getPlayer().getPlayerInfo(),
				this.cards.stream().map(card -> card.getCard()).collect(Collectors.toSet())
			));
		} else {
			this.panel.getChannel().send(new SerpentSpearAttackReactionInGameServerCommand(
				this.panel.getContent().getSelf().getPlayerInfo(),
				this.cards.stream().map(card -> card.getCard()).collect(Collectors.toSet())
			));
		}
	}
	
	@Override
	public void onCanceled() {
		this.onDeactivated();
		this.panel.pushOperation(this.previousOperation);
	}
	
	@Override
	public void onActivated(GamePanel<? extends Hero> panel, Activatable source) {
		this.panel = panel;
		this.source = source;
		
		while (panel.getCurrentOperation() != null) {
			this.previousOperation = panel.getCurrentOperation();
			this.previousOperation.onDeactivated();
		}
		
		this.panel.getContent().setMessage("Select 2 cards to use as an Attack");
		this.panel.getContent().setCancelEnabled(true);
		for (CardUI card : panel.getContent().getCardRackUI().getCardUIs()) {
			card.setActivatable(true);
		}
		if (this.withTarget) {
			for (PlayerUI player : panel.getContent().getOtherPlayersUI()) {
				if (panel.getContent().getSelf().isPlayerInAttackRange(player.getPlayer(), panel.getContent().getNumberOfPlayersAlive())) {
					player.setActivatable(true);
				}
			}
		}
	}
	
	@Override
	public void onDeactivated() {
		this.source.setActivated(false);
		if (this.target != null) {
			this.target.setActivated(false);
		}
		this.cards.forEach(card -> card.setActivated(false));
		for (CardUI card : panel.getContent().getCardRackUI().getCardUIs()) {
			card.setActivatable(false);
		}
		for (PlayerUI player : panel.getContent().getOtherPlayersUI()) {
			player.setActivatable(false);
		}
		this.panel.getContent().clearMessage();
		this.panel.getContent().setConfirmEnabled(false);
		this.panel.getContent().setCancelEnabled(false);
		this.panel.popOperation();
	}

}
