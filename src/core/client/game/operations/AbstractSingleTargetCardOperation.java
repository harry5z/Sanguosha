package core.client.game.operations;

import commands.game.server.ingame.InGameServerCommand;
import core.client.GamePanel;
import core.heroes.Hero;
import core.player.PlayerComplete;
import core.player.PlayerInfo;
import ui.game.interfaces.Activatable;
import ui.game.interfaces.CardUI;
import ui.game.interfaces.ClientGameUI;
import ui.game.interfaces.PlayerUI;

public abstract class AbstractSingleTargetCardOperation implements Operation {
	
	protected GamePanel<? extends Hero> panel;
	protected Activatable activator;
	protected PlayerInfo source;
	protected PlayerUI targetUI;
	
	@Override
	public void onPlayerClicked(PlayerUI player) {
		PlayerInfo info = player.getPlayer().getPlayerInfo();
		if (this.targetUI == null) {
			this.targetUI = player;
			this.targetUI.setActivated(true);
			this.panel.getContent().setConfirmEnabled(true);
		} else if (info.equals(this.targetUI.getPlayer().getPlayerInfo())) {
			this.targetUI.setActivated(false);
			this.targetUI = null;
			this.panel.getContent().setConfirmEnabled(false);
		} else {
			this.targetUI.setActivated(false);
			this.targetUI = player;
			this.targetUI.setActivated(true);
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
		if (this.targetUI != null) {
			this.targetUI.setActivated(false);
			this.panel.getContent().setConfirmEnabled(false);
		}
		for (PlayerUI other : this.panel.getContent().getOtherPlayersUI()) {
			other.setActivatable(false);
		}
		this.panel.getContent().getHeroUI().setActivatable(false);
		this.panel.getContent().setCancelEnabled(false);
		this.panel.getContent().clearMessage();
		this.activator.setActivated(false);
		this.panel.popOperation();
	}
	
	@Override
	public void onConfirmed() {
		this.onCanceled();
		this.panel.getCurrentOperation().onConfirmed();
		this.panel.getChannel().send(this.getCommand());
	}
	
	@Override
	public void onActivated(GamePanel<? extends Hero> panel, Activatable activator) {
		this.activator = activator;
		this.panel = panel;
		ClientGameUI<? extends Hero> panelUI = panel.getContent();
		panelUI.setMessage("Select one target.");
		PlayerComplete self = panelUI.getSelf();
		this.source = self.getPlayerInfo();
		this.setupTargetSelection();
	}
	
	protected abstract InGameServerCommand getCommand();
	
	protected abstract void setupTargetSelection();

}
