package core.client.game.operations;
import java.util.LinkedList;
import java.util.Queue;

import commands.game.server.ingame.InGameServerCommand;
import core.player.PlayerInfo;
import ui.game.interfaces.Activatable;
import ui.game.interfaces.CardUI;
import ui.game.interfaces.PlayerUI;

public abstract class AbstractMultiTargetCardOperation extends AbstractOperation implements MultiTargetOperation {

	protected final Activatable activator;
	protected PlayerInfo source;
	protected Queue<PlayerUI> targets;
	private int minTargets;
	private int maxTargets;
	
	public AbstractMultiTargetCardOperation(Activatable activator, int minTargets, int maxTargets) {
		this.activator = activator;
		this.targets = new LinkedList<>();
		this.minTargets = minTargets;
		this.maxTargets = maxTargets;
	}
	
	@Override
	public final void onPlayerClicked(PlayerUI target) {
		if (this.targets.contains(target)) { // unselect a selected target
			target.setActivated(false);
			this.targets.remove(target);
			if (this.targets.size() < this.minTargets) {
				this.panel.getGameUI().setConfirmEnabled(false);
			}
		} else { // select a new target, unselect the oldest target if exceeding maximum
			if (this.targets.size() == this.maxTargets) {
				this.targets.poll().setActivated(false);
			}
			target.setActivated(true);
			this.targets.add(target);
			if (this.targets.size() >= this.minTargets) {
				this.panel.getGameUI().setConfirmEnabled(true);
			}
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
		if (this.targets.size() >= this.minTargets) {
			this.panel.getGameUI().setConfirmEnabled(true);
		}
		this.onLoadedCustom();
	}
	
	@Override
	public final void onUnloaded() {
		this.activator.setActivatable(false);
		this.activator.setActivated(false);
		this.targets.forEach(target -> target.setActivated(false));
		this.panel.getGameUI().setConfirmEnabled(false);
		this.panel.getGameUI().setCancelEnabled(false);
		this.onUnloadedCustom();
	}

	@Override
	public final int getMaxTargets() {
		return this.maxTargets;
	}
	
	@Override
	public final void addMaxTargets(int num) {
		this.maxTargets += num;
	}
	
	protected abstract InGameServerCommand getCommand();
	
	protected abstract void onLoadedCustom();
	
	protected abstract void onUnloadedCustom();

}
