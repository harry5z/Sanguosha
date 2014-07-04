package listeners;

import cards.Card;

public interface CardDisposalListener 
{
	/**
	 * monitors the usage of card
	 * @param card
	 */
	public void onCardUsed(Card card);
	/**
	 * monitors the disposal of card (may invoke skills)
	 * @param card
	 */
	public void onCardDisposed(Card card);

	/**
	 * refresh the disposal area
	 */
	public void refresh();
}
