package core;

public enum TurnStage {
	START_BEGINNING,
	START,
	DECISION_BEGINNING,
	DECISION,
	DRAW,
	DEAL_BEGINNING,
	DEAL,
	DISCARD_BEGINNING,
	DISCARD,
	DISCARD_END,
	END;
	
	private static final TurnStage[] VALUES = values();
	
	public TurnStage nextStage() {
		return VALUES[(this.ordinal() + 1) % VALUES.length];
	}
	
}
