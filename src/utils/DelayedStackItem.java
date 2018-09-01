package utils;

import cards.Card;

public class DelayedStackItem {
	public final Card delayed;
	public final DelayedType type;
	
	public DelayedStackItem(Card delayed, DelayedType type) {
		this.delayed = delayed;
		this.type = type;
	}
	
	@Override
	public int hashCode() {
		return this.type.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof DelayedStackItem ? this.type == ((DelayedStackItem) obj).type : false;
	}
}