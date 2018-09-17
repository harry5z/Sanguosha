package core.client.game.event;

import core.client.game.operations.basics.InitiateAttackOperation;

public class InitiateAttackClientGameEvent extends AbstractLifecycleClientGameEvent {
	
	public final InitiateAttackOperation operation;

	public InitiateAttackClientGameEvent(boolean isStart, InitiateAttackOperation operation) {
		super(isStart);
		this.operation = operation;
	}

}
