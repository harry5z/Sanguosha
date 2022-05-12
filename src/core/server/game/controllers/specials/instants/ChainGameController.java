package core.server.game.controllers.specials.instants;

import java.util.Queue;

import core.event.game.GameEvent;
import core.player.PlayerCompleteServer;
import core.server.game.Game;

public class ChainGameController extends AbstractMultiTargetInstantSpecialGameController {

	public ChainGameController(PlayerCompleteServer source, Queue<PlayerCompleteServer> targets) {
		super(source, targets);
	}

	@Override
	protected GameEvent getTargetEffectivenessEvent() {
		return null;
	}

	@Override
	protected void takeEffect(Game game) {
		this.currentTarget.chain();
		this.nextStage();
	}

	@Override
	protected String getNeutralizationMessage() {
		return this.source + " used Chain on " + this.currentTarget + ", use Neutralization?";
	}

}
