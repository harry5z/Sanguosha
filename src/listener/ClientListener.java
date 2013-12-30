package listener;

import core.Update;

public interface ClientListener 
{
	/**
	 * On receiving update
	 * @param note
	 */
	public void onNotified(Update update);
}
