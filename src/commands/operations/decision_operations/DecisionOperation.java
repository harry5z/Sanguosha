package commands.operations.decision_operations;

import commands.Command;
import commands.operations.Operation;
import cards.Card;

public abstract class DecisionOperation extends Operation
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6814734281457367294L;

	public DecisionOperation(Command next) {
		super(next);
	}

	public abstract void setResult(Card card);
}
