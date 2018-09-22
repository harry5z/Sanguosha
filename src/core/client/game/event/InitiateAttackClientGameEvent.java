package core.client.game.event;

import core.client.game.operations.MultiTargetOperation;

public class InitiateAttackClientGameEvent extends AbstractLifecycleClientGameEvent {
	
	public final MultiTargetOperation operation;

	public InitiateAttackClientGameEvent(boolean isStart, MultiTargetOperation operation) {
		super(isStart);
		this.operation = operation;
	}

}
