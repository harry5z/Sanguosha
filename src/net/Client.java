package net;

import gui.PanelGui;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import player.PlayerOriginalClientComplete;
import update.NewPlayer;
import update.Update;
/**
 * client side, used by each player to communicate to master
 * @author Harry
 *
 */
public class Client extends Thread
{
	private final ExecutorService executor;
	private static final int POOL_SIZE = Runtime.getRuntime().availableProcessors();
	private int masterPort;
	private String masterHost;
	
	private ObjectOutputStream out;
	
	private PlayerOriginalClientComplete player;
	public Client()
	{
		executor = Executors.newFixedThreadPool(POOL_SIZE);
		masterPort = Master.DEFAULT_PORT;
		masterHost = "localhost";
		player = new PlayerOriginalClientComplete("Player",this);
		executor.execute(new PanelGui(player));
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
	public synchronized void sendToMaster(Update update)
	{
		try
		{
			out.writeObject(update);
		}
		catch(IOException e)//need to handle connection failure in the future
		{
			System.err.println("Client: I/O Exception when sending notification");
			e.printStackTrace();
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
			out.writeObject(new NewPlayer(player));
			System.out.println("Listening to master");
			while(true)
			{
				Update update = (Update)in.readObject();
				update.playerOperation(player);
			}
		}
		catch(IOException e)
		{
			System.err.println("Client: I/O Exception when connecting with master");
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) 
		{
			System.err.println("Client: Received invalid response from master");
			e.printStackTrace();
		}
	}
	public static void main(String[] args)
	{
		new Client().run();
	}
}
