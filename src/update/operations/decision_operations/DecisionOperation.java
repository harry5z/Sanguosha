package update.operations.decision_operations;

import cards.Card;
import update.Update;
import update.operations.Operation;

public abstract class DecisionOperation extends Operation
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6814734281457367294L;

	public DecisionOperation(Update next) {
		super(next);
	}

	public abstract void setResult(Card card);
}
