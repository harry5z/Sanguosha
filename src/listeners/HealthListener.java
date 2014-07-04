package listeners;

public interface HealthListener 
{
	/**
	 * initialize health limit or change health limit
	 * @param limit
	 */
	public void onSetHealthLimit(int limit);
	/**
	 * set current health (supposedly not used often)
	 * @param current
	 */
	public void onSetHealthCurrent(int current);
	/**
	 * used for regular changes of health
	 * @param amount
	 */
	public void onHealthChangedBy(int amount);
	/**
	 * used when player is dead
	 */
	public void onDeath();
}
