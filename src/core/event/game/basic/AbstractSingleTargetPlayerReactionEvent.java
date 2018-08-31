package core.event.game.basic;

import core.event.game.PlayerReactionGameEvent;
import core.player.PlayerInfo;

public abstract class AbstractSingleTargetPlayerReactionEvent
	extends AbstractSingleTargetGameEvent
	implements PlayerReactionGameEvent {
	
	private final String message;
	
	public AbstractSingleTargetPlayerReactionEvent(PlayerInfo target, String message) {
		super(target);
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return this.message;
	}

}
