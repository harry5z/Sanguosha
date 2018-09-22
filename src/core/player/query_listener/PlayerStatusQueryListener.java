package core.player.query_listener;

import core.player.PlayerComplete;
import core.player.query.PlayerStatusQuery;

public interface PlayerStatusQueryListener<T extends PlayerStatusQuery> {
	
	public Class<T> getQueryClass();
	
	public void onQuery(T query, PlayerComplete player);

}
