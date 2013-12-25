package core;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import update.Update;

public class Master extends Thread
{
	private Framework framework;
	private final int mPort;
	private final ExecutorService executor;
	private ArrayList<PlayerThread> threads;
	private int playerCount;
	
	public static final int DEFAULT_PORT = 12345;
	
	private static final int POOL_SIZE = Runtime.getRuntime().availableProcessors();
	
	public Master(int port)
	{
		mPort = port;
		executor = Executors.newFixedThreadPool(POOL_SIZE);
		threads = new ArrayList<PlayerThread>();
		framework = new Framework();
		playerCount = 0;
	}
	public Master()
	{
		mPort = DEFAULT_PORT;
		executor = Executors.newFixedThreadPool(POOL_SIZE);
		threads = new ArrayList<PlayerThread>();
		framework = new Framework();
		playerCount = 0;
	}
	public int getPlayerCount()
	{
		return playerCount;
	}
	public Framework getFramework()
	{
		return framework;
	}
	/**
	 * Register a game framework
	 * @param framework
	 */
	public void registerFramework(Framework framework)
	{
		this.framework = framework;
	}
	public void sendToAllClients(Update update)
	{
		for(PlayerThread p : threads)
			p.sendToClient(update);
	}
	@Override
	public void run()
	{
		ServerSocket serverSocket = null;
		try
		{
			serverSocket = new ServerSocket(mPort);
			System.out.println("Master: waiting for connections...");
			while(true)
			{
				Socket socket = serverSocket.accept();
				System.out.println("Master: receiving new connection...");
				playerCount++;
				PlayerThread newThread = new PlayerThread(socket,this);
				threads.add(newThread);
				executor.execute(newThread);
			}
			
		}
		catch(IOException e)
		{
			System.err.println("Master: I/O Exception");
			e.printStackTrace();
		}
	}
	private class PlayerThread extends Thread
	{
		private Socket socket;
		private ObjectOutputStream out;
		private ObjectInputStream in;
		private Master master;
		
		public PlayerThread(Socket socket,Master master)
		{
			try
			{
				this.socket = socket;
				this.out = new ObjectOutputStream(socket.getOutputStream());
				this.in = new ObjectInputStream(socket.getInputStream());
				this.master = master;
			}
			catch(IOException e)
			{
				System.err.println("PlayerThread: I/O exception");
				e.printStackTrace();
			}
		}
		/**
		 * Send update to client
		 * @param note
		 */
		public void sendToClient(Object obj)
		{
			try 
			{
				out.writeObject(obj);
				out.flush();
			} 
			catch (IOException e) 
			{
				System.err.println("PlayerThread: I/O Exception when updating client");
				e.printStackTrace();
			}
		}
		private void kill()
		{
			try
			{
				in.close();
				out.close();
				socket.close();
			}
			catch(IOException e)
			{
				//do nothing
			}
		}
		@Override
		public void run()
		{
			try
			{
				while(true)//evaluation loop
				{
					Object obj = in.readObject();
					if(obj instanceof Update)
					{
						((Update)obj).masterOperation(master);
					}
					else
					{
						System.err.println("PlayerThread: Unidentified update");
					}
				}
			}
			catch(IOException e)
			{
				System.err.println("PlayerThread: I/O Exception when receiving update");
			}
			catch(ClassNotFoundException e)
			{
				System.err.println("PlayerThread: Class Not Found");
			}
			finally
			{
				System.out.println("PlayerThread: Exiting...");
				kill();
			}
		}
	}

	public static void main(String[] args)
	{
		new Master().run();
	}
}

