package update.operations.decision_operations;

import cards.Card;
import update.operations.Operation;

public interface DecisionOperation extends Operation
{
	public void setResult(Card card);
}
