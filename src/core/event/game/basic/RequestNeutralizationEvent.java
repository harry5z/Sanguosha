package core.event.game.basic;

import core.player.PlayerInfo;

public class RequestNeutralizationEvent extends AbstractSingleTargetGameEvent {

	public RequestNeutralizationEvent(PlayerInfo target) {
		super(target);
	}

}
