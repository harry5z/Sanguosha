package listeners.game;

import cards.Card;
import utils.DelayedType;

public interface DelayedListener {
	
	public void onDelayedAdded(Card card, DelayedType type);
	
	public void onDelayedRemove(DelayedType type);

}
