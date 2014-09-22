package net.client;

import net.Message;

public interface ClientMessageListener {

	public void onMessageReceived(Message message);
}
