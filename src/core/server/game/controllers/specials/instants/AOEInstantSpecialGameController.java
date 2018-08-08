package core.server.game.controllers.specials.instants;

import java.util.HashSet;
import java.util.Set;

import commands.game.client.RequestNeutralizationGameUIClientCommand;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.GameRoom;

public abstract class AOEInstantSpecialGameController extends AbstractInstantSpecialGameController {
	
	protected PlayerCompleteServer currentTarget;
	private Set<PlayerCompleteServer> seenTargets;

	public AOEInstantSpecialGameController(PlayerInfo source, GameRoom room, boolean includeSelf) {
		super(source, room);
		this.seenTargets = new HashSet<>();
		if (includeSelf) {
			this.currentTarget = this.source;
		} else {
			this.currentTarget = this.game.getNextPlayerAlive(this.source);
			this.seenTargets.add(this.source);
		}
	}

	@Override
	public void proceed() {
		switch(stage) {
			case TARGET_LOCKED:
				this.seenTargets.add(this.currentTarget);
				this.stage = this.stage.nextStage();
				this.proceed();
				break;
			case NEUTRALIZATION:
				this.room.sendCommandToAllPlayers(new RequestNeutralizationGameUIClientCommand());
				break;
			case EFFECT:
				if (this.neutralized) {
					this.stage = this.stage.nextStage();
					this.proceed();
				} else {
					this.takeEffect();
				}
				break;
			case EFFECT_TAKEN:
				this.currentTarget.clearDisposalArea();
				PlayerCompleteServer nextTarget = this.game.getNextPlayerAlive(this.currentTarget);
				if (this.seenTargets.contains(nextTarget)) {
					game.popGameController();
					game.getGameController().proceed();
					return;
				}
				this.stage = SpecialStage.TARGET_LOCKED;
				this.neutralized = false;
				this.neutralizedCount = 0;
				this.currentTarget = nextTarget;
				this.proceed();
				break;
		}
	}
	
	protected abstract void takeEffect();

}
