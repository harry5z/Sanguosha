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
		this.nextStage();
	}

	@Override
	protected String getNeutralizationMessage() {
		return this.source + " used Chain on " + this.currentTarget + ", use Neutralization?";
	}

}
