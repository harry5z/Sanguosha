package core.player.query;

public class PlayerAttackTargetLimitQuery implements PlayerStatusQuery {
	
	private int limit;
	
	public PlayerAttackTargetLimitQuery(int initialLimit) {
		this.limit = initialLimit;
	}
	
	public void addTargetLimit(int num) {
		this.limit += num;
	}

	public int getLimit() {
		return this.limit;
	}
}
