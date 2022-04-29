package core.client.game.operations.instants;

import cards.equipments.Equipment.EquipmentType;
import commands.game.server.ingame.InitiateBorrowSwordInGameServerCommand;
import core.client.GamePanel;
import core.client.game.operations.Operation;
import ui.game.CardGui;
import ui.game.interfaces.Activatable;
import ui.game.interfaces.CardUI;
import ui.game.interfaces.GameUI;
import ui.game.interfaces.PlayerUI;

public class BorrowSwordOperation implements Operation {
	
	protected GamePanel panel;
	protected Activatable activator;
	protected PlayerUI targetUI;
	protected PlayerUI attackTargetUI;

	@Override
	public void onPlayerClicked(PlayerUI player) {
		if (this.attackTargetUI != null) {
			this.attackTargetUI.setActivated(false);
			if (this.attackTargetUI == player) {
				// cancel attack target
				this.attackTargetUI = null;
				this.panel.getGameUI().setMessage("Select Attack target");
				this.panel.getGameUI().setConfirmEnabled(false);
			} else {
				// switch attack target
				this.attackTargetUI = player;
				this.attackTargetUI.setActivated(true);
			}
		} else if (this.targetUI != null) {
			if (this.targetUI == player) {
				// cancel Borrow Sword target selection
				this.targetUI.setActivated(false);
				for (PlayerUI other : this.panel.getGameUI().getOtherPlayersUI()) {
					other.setActivatable(false);
					if (other.getPlayer().isEquipped(EquipmentType.WEAPON)) {
						other.setActivatable(true);
					}
				}
				this.panel.getGameUI().getHeroUI().setActivatable(false);
			} else {
				// select attack target
				this.attackTargetUI = player;
				this.attackTargetUI.setActivated(true);
				this.panel.getGameUI().setConfirmEnabled(true);
			}
		} else {
			// select Borrow Sword target
			this.targetUI = player;
			this.targetUI.setActivated(true);
			for (PlayerUI other : this.panel.getGameUI().getOtherPlayersUI()) {
				other.setActivatable(false);
				if (player == other) {
					continue;
				}
				if (player.getPlayer().isPlayerInAttackRange(other.getPlayer(), this.panel.getGameState().getNumberOfPlayersAlive())) {
					other.setActivatable(true);
				}
			}
			if (player.getPlayer().isPlayerInAttackRange(this.panel.getGameState().getSelf(), this.panel.getGameState().getNumberOfPlayersAlive())) {
				this.panel.getGameUI().getHeroUI().setActivatable(true);
			}
			this.panel.getGameUI().setMessage("Select Attack target");
		}
	}
	
	@Override
	public void onCardClicked(CardUI card) {
		this.onCanceled();
		if (card != this.activator) {
			this.panel.getCurrentOperation().onCardClicked(card);
		}
	}
	
	@Override
	public void onEnded() {
		this.onCanceled();
		this.panel.getCurrentOperation().onEnded();
	}
	
	@Override
	public void onCanceled() {
		if (this.attackTargetUI != null) {
			this.attackTargetUI.setActivated(false);
		}
		
		if (this.targetUI != null) {
			this.targetUI.setActivated(false);
			this.panel.getGameUI().setConfirmEnabled(false);
		}
		
		for (PlayerUI other : this.panel.getGameUI().getOtherPlayersUI()) {
			other.setActivatable(false);
		}
		
		this.panel.getGameUI().getHeroUI().setActivatable(false);
		this.panel.getGameUI().setCancelEnabled(false);
		this.panel.getGameUI().clearMessage();
		this.activator.setActivated(false);
		this.panel.popOperation();
	}
	
	@Override
	public void onConfirmed() {
		this.onCanceled();
		this.panel.getCurrentOperation().onConfirmed();
		this.panel.getChannel().send(new InitiateBorrowSwordInGameServerCommand(
			this.targetUI.getPlayer().getPlayerInfo(),
			this.attackTargetUI.getPlayer().getPlayerInfo(),
			((CardGui) this.activator).getCard()
		));
	}
	
	@Override
	public void onActivated(GamePanel panel, Activatable activator) {
		this.activator = activator;
		this.panel = panel;
		GameUI panelUI = panel.getGameUI();
		panelUI.setMessage("Select one target.");
		panelUI.setCancelEnabled(true);
		for (PlayerUI other : this.panel.getGameUI().getOtherPlayersUI()) {
			if (other.getPlayer().isEquipped(EquipmentType.WEAPON)) {
				other.setActivatable(true);
			}
		}
		
	}

}
