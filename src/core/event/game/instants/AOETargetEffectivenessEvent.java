package core.event.game.instants;

import core.event.game.GameEvent;
import core.player.PlayerCompleteServer;

public interface AOETargetEffectivenessEvent extends GameEvent {

	public PlayerCompleteServer getCurrentTarget();
}
