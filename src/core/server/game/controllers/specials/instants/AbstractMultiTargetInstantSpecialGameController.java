package core.server.game.controllers.specials.instants;

import java.util.Queue;

import core.event.game.GameEvent;
import core.event.game.basic.RequestNullificationEvent;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import exceptions.server.game.GameFlowInterruptedException;

public abstract class AbstractMultiTargetInstantSpecialGameController extends AbstractInstantSpecialGameController {
	
	protected PlayerCompleteServer currentTarget;
	private final Queue<PlayerCompleteServer> targets;

	public AbstractMultiTargetInstantSpecialGameController(PlayerCompleteServer source, Queue<PlayerCompleteServer> targets) {
		super(source);
		this.targets = targets;
		this.currentTarget = this.targets.poll();
	}

	@Override
	protected void handleStage(Game game, SpecialStage stage) throws GameFlowInterruptedException {
		switch(stage) {
			case TARGET_LOCKED:
				this.nextStage();
				GameEvent event = this.getTargetEffectivenessEvent();
				if (event != null) {
					game.emit(event);
				}
				break;
			case NULLIFICATION:
				if (this.canBeNullified()) {
					if (this.nullifiedCount >= game.getNumberOfPlayersAlive()) {
						this.nullifiedCount = 0;
						this.nextStage();
					} else {
						if (this.nullifiedCount == 0) {
							game.emit(new RequestNullificationEvent(
								this.currentTarget.getPlayerInfo(),
								this.getNullificationMessage()
							));
						}
						throw new GameFlowInterruptedException();
					}
				} else {
					this.nextStage();
				}
				break;
			case EFFECT:
				if (!this.nullified) {
					this.takeEffect(game);
				} else {
					this.nextStage();
				}
				break;
			case TARGET_SWITCH:
				PlayerCompleteServer next = this.targets.poll();
				while (true) {
					// if no more targets, end the controller
					if (next == null) {
						this.onSettled(game);
						this.nextStage();
						return;
					}
					
					if (!next.isAlive()) {
						// skip all dead targets (due to whatever reasons)
						next = this.targets.poll();
					} else {
						// reset the controller go to the next target
						this.setStage(SpecialStage.TARGET_LOCKED);
						this.nullified = false;
						this.nullifiedCount = 0;
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
	
	protected abstract void takeEffect(Game game) throws GameFlowInterruptedException;
	
	protected boolean canBeNullified() {
		return true;
	}
	
	protected void onSettled(Game game) {}

}