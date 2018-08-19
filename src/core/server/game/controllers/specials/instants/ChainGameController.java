package core.server.game.controllers.specials.instants;

import java.util.Queue;

import core.event.game.GameEvent;
import core.player.PlayerInfo;
import core.server.game.Game;

public class ChainGameController extends MultiTargetInstantSpecialGameController {

	public ChainGameController(PlayerInfo source, Game game, Queue<PlayerInfo> targets) {
		super(source, game, targets);
	}

	@Override
	protected GameEvent getTargetEffectivenessEvent() {
		return null;
	}

	@Override
	protected void takeEffect() {
		this.currentTarget.chain();
		this.stage = this.stage.nextStage();
		this.proceed();
	}

}
