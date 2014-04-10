package core;

import listener.FrameworkListener;

public interface Framework4Gui {

	/**
	 * start the game
	 */
	public void start();
	
	/**
	 * reset the game
	 */
	public void reset();
	
	/**
	 * register a framework listener
	 * @param listener
	 */
	public void registerFrameworkListener(FrameworkListener listener);
}
