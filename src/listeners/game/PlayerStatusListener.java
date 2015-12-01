package listeners.game;

public interface PlayerStatusListener {

	public void onWineUsed();
	
	public void onAttackUsed();
	
	public void onSetAttackLimit(int limit);
	
	public void onSetAttackUsed(int amount);
	
	public void onSetWineUsed(int amount);
	
	public void onResetWineEffective();
	
	public void onFlip(boolean flipped);
	
}
