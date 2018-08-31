package core.event.game.basic;

import core.player.PlayerInfo;

public class RequestNeutralizationEvent extends AbstractSingleTargetPlayerReactionEvent {

	public RequestNeutralizationEvent(PlayerInfo target, String message) {
		super(target, message);
	}

}
