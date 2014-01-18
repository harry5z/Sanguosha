package events.decision_events;

import core.Card;
import core.Operation;

public interface DecisionOperation extends Operation
{
	public void setResult(Card card);
}
