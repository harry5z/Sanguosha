package listener;

import update.Update;

public interface ClientListener 
{
	/**
	 * On receiving update
	 * @param note
	 */
	public void onNotified(Update update);
}
