package net;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import core.Framework;
import core.Update;

public class Master extends Thread
{
	private Framework framework;
	private final int mPort;
	private final ExecutorService executor;
	private ArrayList<PlayerThread> threads;
	
	public static final int DEFAULT_PORT = 12345;
	
	private static final int POOL_SIZE = Runtime.getRuntime().availableProcessors();
	
	public Master(int port)
	{
		mPort = port;
		executor = Executors.newFixedThreadPool(POOL_SIZE);
		threads = new ArrayList<PlayerThread>();
		framework = new Framework(this);
	}
	public Master()
	{
		mPort = DEFAULT_PORT;
		executor = Executors.newFixedThreadPool(POOL_SIZE);
		threads = new ArrayList<PlayerThread>();
		framework = new Framework(this);
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
				PlayerThread newThread = new PlayerThread(socket);
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
	//****** actually too slow *********
//	private class UpdateRunnable implements Runnable
//	{
//		private PlayerThread thread;
//		private Update update;
//		public UpdateRunnable(PlayerThread t)
//		{
//			thread = t;
//		}
//		public void setUpdate(Update update)
//		{
//			this.update = update;
//		}
//		@Override
//		public void run() 
//		{
//			thread.sendToClient(update);
//		}
//		
//	}
	private class PlayerThread extends Thread
	{
		private Socket socket;
		private ObjectOutputStream out;
		private ObjectInputStream in;
		
		public PlayerThread(Socket socket)
		{
			try
			{
				this.socket = socket;
				this.out = new ObjectOutputStream(socket.getOutputStream());
				this.in = new ObjectInputStream(socket.getInputStream());
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
						((Update)obj).frameworkOperation(framework);
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

