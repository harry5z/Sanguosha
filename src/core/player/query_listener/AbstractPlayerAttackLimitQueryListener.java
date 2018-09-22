package core.player.query_listener;

import core.player.query.PlayerStatusQuery;

public abstract class AbstractPlayerAttackLimitQueryListener<T extends PlayerStatusQuery> implements PlayerStatusQueryListener<T> {

	@Override
	public int hashCode() {
		return this.getClass().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.getClass().equals(obj.getClass());
	}
}
