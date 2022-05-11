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
	protected void handleStage(SpecialStage stage) throws GameFlowInterruptedException {
		switch(stage) {
			case TARGET_LOCKED:
				this.nextStage();
				GameEvent event = this.getTargetEffectivenessEvent();
				if (event != null) {
					this.game.emit(event);
				}
				break;
			case NEUTRALIZATION:
				if (this.canBeNeutralized()) {
					if (this.neutralizedCount >= this.game.getNumberOfPlayersAlive()) {
						this.neutralizedCount = 0;
						this.nextStage();
					} else {
						if (this.neutralizedCount == 0) {
							this.game.emit(new RequestNeutralizationEvent(
								this.currentTarget.getPlayerInfo(),
								this.getNeutralizationMessage()
							));
						}
						throw new GameFlowInterruptedException();
					}
				} else {
					this.nextStage();
				}
				break;
			case EFFECT:
				if (!this.neutralized) {
					this.takeEffect();
				} else {
					this.nextStage();
				}
				break;
			case TARGET_SWITCH:
				PlayerCompleteServer next = this.targets.poll();
				while (true) {
					// if no more targets, end the controller
					if (next == null) {
						this.onSettled();
						this.nextStage();
						return;
					}
					
					if (!next.isAlive()) {
						// skip all dead targets (due to whatever reasons)
						next = this.targets.poll();
					} else {
						// reset the controller go to the next target
						this.setStage(SpecialStage.TARGET_LOCKED);
						this.neutralized = false;
						this.neutralizedCount = 0;
						this.currentTarget = next;
						break;
					}
				}
				break;
			case END:
				break;
		}
	}

	protected abstract GameEvent getTargetEffectivenessEvent();
	
	protected abstract void takeEffect() throws GameFlowInterruptedException;
	
	protected boolean canBeNeutralized() {
		return true;
	}
	
	protected void onSettled() {}

}
