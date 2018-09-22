package core.client.game.operations;

public interface MultiTargetOperation extends Operation {

	public int getMaxTargets();
	
	public void addMaxTargets(int num);
}
