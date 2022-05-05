package core.client.game.event;

import core.client.game.operations.TargetEditableOperation;

public class InitiateAttackClientGameEvent extends AbstractLifecycleClientGameEvent {
	
	public final TargetEditableOperation operation;

	public InitiateAttackClientGameEvent(boolean isStart, TargetEditableOperation operation) {
		super(isStart);
		this.operation = operation;
	}

}
