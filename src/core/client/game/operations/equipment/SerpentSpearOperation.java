package core.client.game.operations.equipment;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import cards.equipments.Equipment.EquipmentType;
import commands.game.server.ingame.SerpentSpearAttackReactionInGameServerCommand;
import commands.game.server.ingame.SerpentSpearInitiateAttackInGameServerCommand;
import core.client.game.operations.AbstractOperation;
import ui.game.interfaces.Activatable;
import ui.game.interfaces.CardUI;
import ui.game.interfaces.EquipmentUI;
import ui.game.interfaces.PlayerUI;

public class SerpentSpearOperation extends AbstractOperation {
	
	private final EquipmentUI activator;
	private final boolean hasTarget;
	private PlayerUI target;
	private Queue<CardUI> cards;
	
	public SerpentSpearOperation(Activatable source, boolean hasTarget) {
		this.activator = (EquipmentUI) source;
		this.hasTarget = hasTarget;
		this.target = null;
		this.cards = new LinkedList<>();
	}
	
	@Override
	public void onCardClicked(CardUI card) {
		if (this.cards.contains(card)) {
			card.setActivated(false);
			this.cards.remove(card);
			this.panel.getGameUI().setConfirmEnabled(false);
			return;
		}
		
		if (this.cards.size() == 2) {
			CardUI first = this.cards.poll();
			first.setActivated(false);
		}
		this.cards.add(card);
		card.setActivated(true);
		
		if (this.cards.size() == 2) {
			if (!this.hasTarget || (this.hasTarget && this.target != null)) {
				this.panel.getGameUI().setConfirmEnabled(true);
			}
		}
	}
	
	@Override
	public void onPlayerClicked(PlayerUI player) {
		if (this.target == player) {
			this.target.setActivated(false);
			this.target = null;
			this.panel.getGameUI().setConfirmEnabled(false);
		} else if (this.target != null) {
			this.target.setActivated(false);
			this.target = player;
			this.target.setActivated(true);
		} else {
			this.target = player;
			this.target.setActivated(true);
			if (this.cards.size() == 2) {
				this.panel.getGameUI().setConfirmEnabled(true);
			}
		}
	}
	
	@Override
	public void onConfirmed() {
		this.onUnloaded();
		this.onDeactivated();
		if (this.hasTarget) {
			this.panel.getChannel().send(new SerpentSpearInitiateAttackInGameServerCommand(
				this.panel.getGameState().getSelf().getPlayerInfo(),
				Set.of(this.target.getPlayer().getPlayerInfo()),
				this.cards.stream().map(card -> card.getCard()).collect(Collectors.toSet())
			));
		} else {
			this.panel.getChannel().send(new SerpentSpearAttackReactionInGameServerCommand(
				this.panel.getGameState().getSelf().getPlayerInfo(),
				this.cards.stream().map(card -> card.getCard()).collect(Collectors.toSet())
			));
		}

	}
	
	@Override
	public void onLoaded() {
		this.activator.setActivatable(true);
		this.activator.setActivated(true);
		// By implementation, this must be the SerpentSpear itself
		// Behave as if CANCEL is clicked
		this.panel.getGameUI().getEquipmentRackUI().setActivatable(Set.of(EquipmentType.WEAPON), equipment -> this.onCanceled());
		this.panel.getGameUI().setMessage("Select 2 cards to use as an Attack");
		this.panel.getGameUI().setCancelEnabled(true);
		this.panel.getGameUI().getCardRackUI().getCardUIs().forEach(card -> card.setActivatable(true));
		if (this.hasTarget) {
			for (PlayerUI player : panel.getGameUI().getOtherPlayersUI()) {
				if (panel.getGameState().getSelf().isPlayerInAttackRange(player.getPlayer(), panel.getGameState().getNumberOfPlayersAlive())) {
					player.setActivatable(true);
				}
			}
		}
	}
	
	@Override
	public void onUnloaded() {
		this.activator.setActivatable(false);
		this.activator.setActivated(false);
		this.panel.getGameUI().getEquipmentRackUI().setUnactivatable(Set.of(EquipmentType.WEAPON));
		if (this.target != null) {
			this.target.setActivated(false);
		}
		this.cards.forEach(card -> card.setActivated(false));
		for (CardUI card : panel.getGameUI().getCardRackUI().getCardUIs()) {
			card.setActivatable(false);
		}
		for (PlayerUI player : panel.getGameUI().getOtherPlayersUI()) {
			player.setActivatable(false);
		}
		this.panel.getGameUI().clearMessage();
		this.panel.getGameUI().setConfirmEnabled(false);
		this.panel.getGameUI().setCancelEnabled(false);
	}

}
