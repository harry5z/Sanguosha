package core.server.game.controllers.specials.instants;

import java.util.LinkedList;
import java.util.Queue;

import core.event.game.GameEvent;
import core.event.game.basic.RequestNeutralizationEvent;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.game.Game;
import exceptions.server.game.GameFlowInterruptedException;

public abstract class MultiTargetInstantSpecialGameController extends AbstractInstantSpecialGameController {
	
	protected PlayerCompleteServer currentTarget;
	private final Queue<PlayerCompleteServer> targets;

	public MultiTargetInstantSpecialGameController(PlayerInfo source, Game game, Queue<PlayerInfo> targets) {
		super(source, game);
		this.targets = new LinkedList<>();
		for (PlayerInfo player : targets) {
			this.targets.add(game.findPlayer(player));
		}
		this.currentTarget = this.targets.poll();
	}

	@Override
	public void proceed() {
		switch(stage) {
			case TARGET_LOCKED:
				try {
					GameEvent event = this.getTargetEffectivenessEvent();
					if (event != null) {
						this.game.emit(event);
					}
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
				PlayerCompleteServer next = this.targets.poll();
				while (true) {
					if (next == null) {
						this.game.popGameController();
						this.onSettled();
						this.game.getGameController().proceed();
						return;
					}
					
					if (next.isAlive()) {
						break;
					} else {
						next = this.targets.poll();
					}
				}
				this.stage = SpecialStage.TARGET_LOCKED;
				this.neutralized = false;
				this.neutralizedCount = 0;
				this.currentTarget = next;
				this.proceed();
				break;
		}
	}
	
	public void setStage(SpecialStage stage) {
		this.stage = stage;
	}
	
	protected abstract GameEvent getTargetEffectivenessEvent();
	
	protected abstract void takeEffect();
	
	protected boolean canBeNeutralized() {
		return true;
	}
	
	protected void onSettled() {}

}
