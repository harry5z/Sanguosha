package core.server.game.controllers.specials.instants;

import core.event.game.instants.AOETargetEffectivenessEvent;
import core.event.game.instants.BrotherhoodTargetEffectivenessEvent;
import core.player.PlayerInfo;
import core.server.game.Game;
import core.server.game.controllers.HealGameController;

public class BrotherhoodGameController extends AOEInstantSpecialGameController {

	public BrotherhoodGameController(PlayerInfo source, Game game) {
		super(source, game, true);
	}

	@Override
	protected void takeEffect() {
		this.stage = this.stage.nextStage();
		if (this.currentTarget.isDamaged()) {
			this.game.pushGameController(new HealGameController(this.source.getPlayerInfo(), this.currentTarget.getPlayerInfo(), this.game));
		}
		this.game.getGameController().proceed();
	}
	
	@Override
	protected boolean canBeNeutralized() {
		// only consider players that is not at full health
		return this.currentTarget.isDamaged();
	}

	@Override
	protected AOETargetEffectivenessEvent getTargetEffectivenessEvent() {
		return new BrotherhoodTargetEffectivenessEvent(this.currentTarget, this);
	}

}
