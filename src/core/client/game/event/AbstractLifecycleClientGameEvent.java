package core.client.game.event;

public abstract class AbstractLifecycleClientGameEvent implements ClientGameEvent {
	
	private final boolean isStart;

	public AbstractLifecycleClientGameEvent(boolean isStart) {
		this.isStart = isStart;
	}
	
	public boolean isStart() {
		return this.isStart;
	}
}
