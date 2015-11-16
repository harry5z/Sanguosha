package commands.operations.decision;

import cards.Card;

import commands.Command;
import commands.operations.Operation;

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
