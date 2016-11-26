package core.heroes.skills;

import cards.Card;
import core.GameState;

public interface Skill {

	public String getName();

	public String getDescription();
	
	default public boolean canBeTargeted(Card card, GameState state) {
		return canBeTargeted(state);
	}
	
	public boolean canBeTargeted(GameState state);
}
