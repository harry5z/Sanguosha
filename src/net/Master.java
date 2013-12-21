package net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import update.Update;
import core.Framework;

public class Master extends Thread
{
	private Framework framework;
	private final int mPort;
	private final ExecutorService executor;
	private ArrayList<PlayerThread> threads;
	
	private static final int POOL_SIZE = Runtime.getRuntime().availableProcessors();
	
	public Master(int port)
	{
		mPort = port;
		executor = Executors.newFixedThreadPool(POOL_SIZE);
		threads = new ArrayList<PlayerThread>();
	}
	/**
	 * Register a game framework
	 * @param framework
	 */
	public void registerFramework(Framework framework)
	{
		this.framework = framework;
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
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
				PlayerThread newThread = new PlayerThread(socket,out,in);
				threads.add(newThread);
				executor.execute(newThread);
			}
			
		}
		catch(IOException e)
		{
			System.err.println("Master: I/O Exception");
		}
	}
	private class PlayerThread extends Thread
	{
		private Socket socket;
		private ObjectOutputStream out;
		private ObjectInputStream in;
		
		public PlayerThread(Socket socket,ObjectOutputStream out,ObjectInputStream in)
		{
			this.socket = socket;
			this.out = out;
			this.in = in;
		}
		/**
		 * Send update to client
		 * @param note
		 */
		public void sendToClient(Update update)
		{
			try 
			{
				out.writeObject(update);
			} 
			catch (IOException e) 
			{
				System.err.println("PlayerThread: I/O Exception when updating client");
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
				while(true)
				{
					Object obj = in.readObject();
					if(!(obj instanceof Update))
						continue;
					Update update = (Update)obj;
					if(update.getMessage().equals(Update.GAME_OVER))
						break;
					framework.onNotified((Update)obj);
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
		new Master(15).run();
	}
}

