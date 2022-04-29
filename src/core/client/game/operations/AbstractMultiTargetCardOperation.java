package core.client.game.operations;
import java.util.LinkedList;
import java.util.Queue;

import commands.game.server.ingame.InGameServerCommand;
import core.client.GamePanel;
import core.player.PlayerComplete;
import core.player.PlayerInfo;
import ui.game.interfaces.Activatable;
import ui.game.interfaces.CardUI;
import ui.game.interfaces.PlayerUI;

public abstract class AbstractMultiTargetCardOperation implements MultiTargetOperation {

	protected GamePanel panel;
	protected Activatable activator;
	protected PlayerInfo source;
	protected Queue<PlayerUI> targets;
	private int minTargets;
	private int maxTargets;
	
	public AbstractMultiTargetCardOperation(int minTargets, int maxTargets) {
		this.targets = new LinkedList<>();
		this.minTargets = minTargets;
		this.maxTargets = maxTargets;
	}
	
	@Override
	public void onPlayerClicked(PlayerUI target) {
		if (this.targets.contains(target)) {
			target.setActivated(false);
			this.targets.remove(target);
			if (this.targets.size() < this.minTargets) {
				this.panel.getGameUI().setConfirmEnabled(false);
			}
		} else {
			if (this.targets.size() == this.maxTargets) {
				PlayerUI oldest = this.targets.poll();
				oldest.setActivated(false);
			}
			target.setActivated(true);
			this.targets.add(target);
			if (this.targets.size() >= this.minTargets) {
				this.panel.getGameUI().setConfirmEnabled(true);
			}
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
		this.onDeactivated();
		this.panel.getCurrentOperation().onConfirmed();
		this.panel.getChannel().send(this.getCommand());
	}
	
	@Override
	public void onActivated(GamePanel panel, Activatable activator) {
		this.activator = activator;
		this.panel = panel;
		PlayerComplete self = panel.getGameState().getSelf();
		this.source = self.getPlayerInfo();
		this.setupTargetSelection();
		if (this.targets.size() >= this.minTargets) {
			panel.getGameUI().setConfirmEnabled(true);
		}
	}
	
	@Override
	public void onDeactivated() {
		this.targets.forEach(target -> target.setActivated(false));
		this.panel.getGameUI().setConfirmEnabled(false);
		for (PlayerUI other : this.panel.getGameUI().getOtherPlayersUI()) {
			other.setActivatable(false);
		}
		this.panel.getGameUI().getHeroUI().setActivatable(false);
		this.panel.getGameUI().setCancelEnabled(false);
		this.activator.setActivated(false);
		this.panel.popOperation();
	}

	@Override
	public int getMaxTargets() {
		return this.maxTargets;
	}
	
	@Override
	public void addMaxTargets(int num) {
		this.maxTargets += num;
	}
	
	protected abstract InGameServerCommand getCommand();
	
	protected abstract void setupTargetSelection();

}
