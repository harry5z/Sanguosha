package core.client.game.operations;

import core.client.GamePanel;

public abstract class AbstractOperation implements Operation {
	
	protected GamePanel panel;
	
	@Override
	public void onConfirmed() {
		this.onUnloaded();
		this.onDeactivated();
	}

	@Override
	public void onCanceled() {
		this.onUnloaded();
		this.panel.popOperation();
		if (this.panel.getCurrentOperation() != null) {
			this.panel.getCurrentOperation().onLoaded();
		}
	}
	
	@Override
	public void onEnded() {
		this.onUnloaded();
		this.panel.popOperation();
		if (this.panel.getCurrentOperation() != null) {
			this.panel.getCurrentOperation().onEnded();
		}
	}

	@Override
	public final void onActivated(GamePanel panel) {
		this.panel = panel;
		this.onLoaded();
	}
	
	@Override
	public void onDeactivated() {
		this.panel.popOperation();
		if (this.panel.getCurrentOperation() != null) {
			this.panel.getCurrentOperation().onDeactivated();
		}
	}

}
