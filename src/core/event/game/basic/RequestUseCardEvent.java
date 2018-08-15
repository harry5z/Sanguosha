package core.event.game.basic;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.function.Predicate;

import cards.Card;
import cards.Card.Suit;
import core.player.PlayerInfo;

public class RequestUseCardEvent extends AbstractSingleTargetGameEvent {
	
	private Collection<RequestUseCardPredicate> predicates;
	
	public static interface RequestUseCardPredicate extends Predicate<Card>, Serializable {
		
		public static RequestUseCardPredicate sameSuit(Suit suit) {
			return card -> card.getSuit() == suit;
		}
		
	}
	
	public RequestUseCardEvent(PlayerInfo target) {
		super(target);
		this.predicates = new HashSet<>();
	}
	
	public RequestUseCardEvent addPredicate(RequestUseCardPredicate predicate) {
		this.predicates.add(predicate);
		return this;
	}
	
	public Collection<RequestUseCardPredicate> getPredicates() {
		return this.predicates;
	}

}
