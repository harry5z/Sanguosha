package core.player.query;

public class PlayerAttackLimitQuery implements PlayerStatusQuery {

	private int limit;
	
	public PlayerAttackLimitQuery(int initialLimit) {
		this.limit = initialLimit;
	}
	
	public void addLimit(int num) {
		this.limit += num;
	}
	
	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getLimit() {
		return this.limit;
	}
}
