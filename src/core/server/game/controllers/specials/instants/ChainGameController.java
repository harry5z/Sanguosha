package core.server.game.controllers.specials.instants;

import java.util.Queue;

import core.event.game.GameEvent;
import core.player.PlayerCompleteServer;
import core.server.game.BattleLog;
import core.server.game.GameInternal;

public class ChainGameController extends AbstractMultiTargetInstantSpecialGameController {

	public ChainGameController(PlayerCompleteServer source, Queue<PlayerCompleteServer> targets) {
		super(source, targets);
	}

	@Override
	protected GameEvent getTargetEffectivenessEvent() {
		return null;
	}

	@Override
	protected void takeEffect(GameInternal game) {
		this.currentTarget.chain();
		game.log(BattleLog.playerADidX(currentTarget, "is now <b>" + (currentTarget.isChained() ? "Chained" : "Unchained") + "</b>"));
		this.nextStage();
	}

	@Override
	protected String getNullificationMessage() {
		return this.source + " used Chain on " + this.currentTarget + ", use Nullification?";
	}

}
