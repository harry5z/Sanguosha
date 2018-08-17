package core.event.game.instants;

import java.util.Map;

import cards.Card;
import core.event.game.basic.AbstractSingleTargetGameEvent;
import core.player.PlayerInfo;

public class HarvestCardSelectionEvent extends AbstractSingleTargetGameEvent {
	
	private Map<Card, Boolean> selectableCards;

	public HarvestCardSelectionEvent(PlayerInfo target, Map<Card, Boolean> selectableCards) {
		super(target);
		this.selectableCards = selectableCards;
	}
	
	public Map<Card, Boolean> getSelectableCards() {
		return this.selectableCards;
	}

}
