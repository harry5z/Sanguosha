package net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import update.Update;
import core.GameListener;

public class Client extends Thread
{
	private int masterPort;
	private String masterHost;
	
	private GameListener listener;
	private ObjectOutputStream out;
	
	public Client(GameListener listener)
	{
		this.listener = listener;
	}
	/**
	 * Set master's port to connect
	 * @param port
	 */
	public void setPort(int port)
	{
		masterPort = port;
	}
	/**
	 * Set master's host to connect
	 * @param host
	 */
	public void setHost(String host)
	{
		masterHost = host;
	}
	/**
	 * Send a notification/game update to master
	 * @param note
	 */
	public void notifyMaster(Update note)
	{
		try
		{
			out.writeObject(note);
		}
		catch(IOException e)//need to handle connection failure in the future
		{
			System.err.println("Client: I/O Exception when sending notification");
		}
	}
	@Override
	public void run()
	{
		Socket socket = null;
		ObjectInputStream in;
		try
		{
			socket = new Socket(masterHost,masterPort);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			listener.onClientRegistered(this);
			while(true)
			{
				Update note = (Update)in.readObject();
				listener.onNotified(note);
				
			}
		}
		catch(IOException e)
		{
			System.err.println("Client: I/O Exception when connecting with master");
		} 
		catch (ClassNotFoundException e) 
		{
			System.err.println("Client: Received invalid response from master");
		}
	}
}
