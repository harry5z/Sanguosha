package core.client.game.operations;

import commands.game.server.ingame.InGameServerCommand;
import core.client.GamePanel;
import core.player.PlayerComplete;
import core.player.PlayerInfo;
import ui.game.interfaces.Activatable;
import ui.game.interfaces.CardUI;
import ui.game.interfaces.PlayerUI;

public abstract class AbstractSingleTargetCardOperation implements Operation {
	
	protected GamePanel panel;
	protected Activatable activator;
	protected PlayerInfo source;
	protected PlayerUI targetUI;
	
	@Override
	public void onPlayerClicked(PlayerUI player) {
		PlayerInfo info = player.getPlayer().getPlayerInfo();
		if (this.targetUI == null) {
			this.targetUI = player;
			this.targetUI.setActivated(true);
			this.panel.getGameUI().setConfirmEnabled(true);
		} else if (info.equals(this.targetUI.getPlayer().getPlayerInfo())) {
			this.targetUI.setActivated(false);
			this.targetUI = null;
			this.panel.getGameUI().setConfirmEnabled(false);
		} else {
			this.targetUI.setActivated(false);
			this.targetUI = player;
			this.targetUI.setActivated(true);
		}
	}
	
	@Override
	public void onCardClicked(CardUI card) {
		this.onDeactivated();
		if (card != this.activator) {
			this.panel.getCurrentOperation().onCardClicked(card);
		}
	}
	
	@Override
	public void onEnded() {
		this.onDeactivated();
		this.panel.getCurrentOperation().onEnded();
	}
	
	@Override
	public void onCanceled() {
		this.onDeactivated();
	}
	
	@Override
	public void onConfirmed() {
		this.onCanceled();
		this.panel.getCurrentOperation().onConfirmed(); // DealOperation
		this.panel.getChannel().send(this.getCommand());
	}
	
	@Override
	public void onActivated(GamePanel panel, Activatable activator) {
		this.activator = activator;
		this.panel = panel;
		panel.getGameUI().setMessage("Select one target.");
		PlayerComplete self = panel.getGameState().getSelf();
		this.source = self.getPlayerInfo();
		this.setupTargetSelection();
	}
	
	@Override
	public void onDeactivated() {
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
	
	protected abstract InGameServerCommand getCommand();
	
	protected abstract void setupTargetSelection();

}
