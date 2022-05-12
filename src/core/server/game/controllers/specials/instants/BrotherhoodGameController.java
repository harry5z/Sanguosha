package core.server.game.controllers.specials.instants;

import java.util.Queue;

import core.event.game.instants.AOETargetEffectivenessEvent;
import core.event.game.instants.BrotherhoodTargetEffectivenessEvent;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import core.server.game.controllers.mechanics.HealGameController;

public class BrotherhoodGameController extends AbstractMultiTargetInstantSpecialGameController {

	public BrotherhoodGameController(PlayerCompleteServer source, Queue<PlayerCompleteServer> targets) {
		super(source, targets);
	}

	@Override
	protected void takeEffect(Game game) {
		this.nextStage();
		if (this.currentTarget.isDamaged()) {
			game.pushGameController(new HealGameController(this.source, this.currentTarget));
		}
	}
	
	@Override
	protected String getNullificationMessage() {
		return this.source + " used Brotherhood on " + this.currentTarget + ", use Nullification?";
	}
	
	@Override
	protected boolean canBeNullified() {
		// only consider players that is not at full health
		return this.currentTarget.isDamaged();
	}

	@Override
	protected AOETargetEffectivenessEvent getTargetEffectivenessEvent() {
		return new BrotherhoodTargetEffectivenessEvent(this.currentTarget, this);
	}

}
