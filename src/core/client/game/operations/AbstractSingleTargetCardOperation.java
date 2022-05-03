package core.client.game.operations;

import commands.game.server.ingame.InGameServerCommand;
import core.player.PlayerInfo;
import ui.game.interfaces.Activatable;
import ui.game.interfaces.CardUI;
import ui.game.interfaces.PlayerUI;

public abstract class AbstractSingleTargetCardOperation extends AbstractOperation {
	
	protected final Activatable activator;
	protected PlayerInfo source;
	protected PlayerUI targetUI;
	
	public AbstractSingleTargetCardOperation(Activatable card) {
		this.activator = card;
	}
	
	@Override
	public final void onPlayerClicked(PlayerUI player) {
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
	public final void onCardClicked(CardUI card) {
		// Only the currently selected card should be clickable by implementation
		// Behave as if the CANCEL button is pressed
		this.onCanceled();
	}
	
	@Override
	public final void onConfirmed() {
		super.onConfirmed();
		this.panel.getChannel().send(this.getCommand());
	}
	
	@Override
	public final void onLoaded() {
		this.activator.setActivatable(true);
		this.activator.setActivated(true);
		this.source = this.panel.getGameState().getSelf().getPlayerInfo();
		this.panel.getGameUI().setCancelEnabled(true);
		this.panel.getGameUI().setMessage("Select a target.");
		this.onLoadedCustom();
	}
	
	@Override
	public final void onUnloaded() {
		this.activator.setActivatable(false);
		this.activator.setActivated(false);
		if (this.targetUI != null) {
			this.targetUI.setActivated(false);
			this.panel.getGameUI().setConfirmEnabled(false);
		}
		this.panel.getGameUI().setCancelEnabled(false);
		this.panel.getGameUI().clearMessage();
		this.onUnloadedCustom();
	}
	
	protected abstract InGameServerCommand getCommand();
	
	protected abstract void onLoadedCustom();
	
	protected abstract void onUnloadedCustom();

}
