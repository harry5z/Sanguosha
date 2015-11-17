package core.server.game;

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
	
	public TurnStage nextStage() {
		switch (this) {
			case START_BEGINNING:
				return START;
			case START:
				return DECISION_BEGINNING;
			case DECISION_BEGINNING:
				return DECISION;
			case DECISION:
				return DRAW;
			case DRAW:
				return DEAL_BEGINNING;
			case DEAL_BEGINNING:
				return DEAL;
			case DEAL:
				return DISCARD_BEGINNING;
			case DISCARD_BEGINNING:
				return DISCARD;
			case DISCARD:
				return DISCARD_END;
			case DISCARD_END:
				return END;
			case END:
				return START_BEGINNING;
			default:
				return null;
		}
	}
	
}
