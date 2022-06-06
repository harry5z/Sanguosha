package core.client.game.operations.instants;

import cards.equipments.Equipment.EquipmentType;
import commands.server.ingame.InitiateBorrowSwordInGameServerCommand;
import core.client.game.operations.AbstractOperation;
import ui.game.CardGui;
import ui.game.interfaces.Activatable;
import ui.game.interfaces.CardUI;
import ui.game.interfaces.GameUI;
import ui.game.interfaces.PlayerUI;

public class BorrowSwordOperation extends AbstractOperation {
	
	private final Activatable activator;
	private PlayerUI cardTarget; // Target of this BorrowSword card
	private PlayerUI attackTarget; // Target who the target of this card must attack
	
	public BorrowSwordOperation(Activatable activator) {
		this.activator = activator;
	}
	
	@Override
	public void onCardClicked(CardUI card) {
		// Only the currently selected card should be clickable by implementation
		// Behave as if the CANCEL button is pressed
		this.onCanceled();
	}

	@Override
	public void onPlayerClicked(PlayerUI player) {
		if (this.attackTarget != null) {
			this.attackTarget.setActivated(false);
			if (this.attackTarget == player) { // cancel attack target
				this.attackTarget = null;
				this.panel.getGameUI().setMessage("Select Attack target");
				this.panel.getGameUI().setConfirmEnabled(false);
			} else { // switch attack target
				this.attackTarget = player;
				this.attackTarget.setActivated(true);
			}
		} else if (this.cardTarget != null) {
			if (this.cardTarget == player) { // cancel Borrow Sword target selection
				this.cardTarget.setActivated(false);
				for (PlayerUI other : this.panel.getGameUI().getOtherPlayersUI()) {
					if (other.getPlayer().isEquipped(EquipmentType.WEAPON)) {
						other.setActivatable(true);
					} else {
						other.setActivatable(false);
					}
				}
				this.panel.getGameUI().getHeroUI().setActivatable(false);
			} else { // select attack target
				this.attackTarget = player;
				this.attackTarget.setActivated(true);
				this.panel.getGameUI().setConfirmEnabled(true);
			}
		} else { // select Borrow Sword target
			this.cardTarget = player;
			this.cardTarget.setActivated(true);
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
	public void onConfirmed() {
		super.onConfirmed();
		this.panel.sendResponse(new InitiateBorrowSwordInGameServerCommand(
			this.cardTarget.getPlayer().getPlayerInfo(),
			this.attackTarget.getPlayer().getPlayerInfo(),
			((CardGui) this.activator).getCard()
		));
	}
	
	@Override
	public void onLoaded() {
		this.activator.setActivatable(true);
		this.activator.setActivated(true);
		GameUI panelUI = panel.getGameUI();
		panelUI.setMessage("Select one target.");
		panelUI.setCancelEnabled(true);
		for (PlayerUI other : this.panel.getGameUI().getOtherPlayersUI()) {
			if (other.getPlayer().isEquipped(EquipmentType.WEAPON)) {
				other.setActivatable(true);
			}
		}
	}
	
	@Override
	public void onUnloaded() {
		this.activator.setActivatable(false);
		this.activator.setActivated(false);
		this.panel.getGameUI().clearMessage();
		this.panel.getGameUI().setCancelEnabled(false);

		if (this.attackTarget != null) {
			this.attackTarget.setActivated(false);
		}
		
		if (this.cardTarget != null) {
			this.cardTarget.setActivated(false);
			this.panel.getGameUI().setConfirmEnabled(false);
		}
		
		for (PlayerUI other : this.panel.getGameUI().getOtherPlayersUI()) {
			other.setActivatable(false);
		}
		
		this.panel.getGameUI().getHeroUI().setActivatable(false);
	}

}
