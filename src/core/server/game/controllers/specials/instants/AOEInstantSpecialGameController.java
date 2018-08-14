package core.server.game.controllers.specials.instants;

import java.util.HashSet;
import java.util.Set;

import core.event.game.basic.RequestNeutralizationEvent;
import core.event.game.instants.AOETargetEffectivenessEvent;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.game.Game;
import exceptions.server.game.GameFlowInterruptedException;

public abstract class AOEInstantSpecialGameController extends AbstractInstantSpecialGameController {
	
	protected PlayerCompleteServer currentTarget;
	private Set<PlayerCompleteServer> seenTargets;

	public AOEInstantSpecialGameController(PlayerInfo source, Game game, boolean includeSelf) {
		super(source, game);
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
				try {
					this.game.emit(this.getTargetEffectivenessEvent());
					this.stage = this.stage.nextStage();
					this.proceed();
				} catch (GameFlowInterruptedException e) {
					e.resume();
				}
				break;
			case NEUTRALIZATION:
				if (this.canBeNeutralized()) {
					try {
						this.game.emit(new RequestNeutralizationEvent(this.currentTarget.getPlayerInfo()));
					} catch (GameFlowInterruptedException e) {
						e.resume();
					}
				} else {
					this.stage = this.stage.nextStage();
					this.proceed();
				}
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
	
	public void setStage(SpecialStage stage) {
		this.stage = stage;
	}
	
	protected abstract AOETargetEffectivenessEvent getTargetEffectivenessEvent();
	
	protected abstract void takeEffect();
	
	protected boolean canBeNeutralized() {
		return true;
	}

}
