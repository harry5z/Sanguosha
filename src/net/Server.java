package net;

import gui.FrameworkGui;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

import update.Update;
import core.FrameworkImpl;
/**
 * master side, used by framework to communicate with all players
 * @author Harry
 *
 */
public class Server extends Thread
{
	private FrameworkImpl framework;
	private final int mPort;
	private final ExecutorService executor;
	private ArrayList<PlayerThread> threads;
	
	public static final int DEFAULT_PORT = 12345;
	public Server(int port)
	{
		mPort = port;
		executor = Executors.newCachedThreadPool();
		threads = new ArrayList<PlayerThread>();
		framework = new FrameworkImpl(this);
		executor.execute(new FrameworkGui(framework));
	}
	/**
	 * initialize server with DEFAULT_PORT
	 */
	public Server()
	{
		mPort = DEFAULT_PORT;
		executor = Executors.newCachedThreadPool();
		threads = new ArrayList<PlayerThread>();
		framework = new FrameworkImpl(this);
		executor.execute(new FrameworkGui(framework));
	}
	/**
	 * Register a game framework
	 * @param framework
	 */
	public void registerFramework(FrameworkImpl framework)
	{
		this.framework = framework;
	}
	/**
	 * inform all clients of an update, call update.playerOperation automatically
	 * @param update
	 */
	public synchronized void sendToAllClients(Update update)
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
		catch(RejectedExecutionException e)
		{
			e.printStackTrace();
		}
		catch(NullPointerException e)
		{
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
				threads.remove(this);
				e.printStackTrace();
			}
		}
		/**
		 * Send update to client
		 * @param update
		 */
		public void sendToClient(Update update)
		{
			try 
			{
				out.writeObject(update);
				out.flush();
			} 
			catch (IOException e) 
			{
				System.err.println("PlayerThread: I/O Exception when updating client");
				threads.remove(this);
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
				threads.remove(this);
			}
			catch(IOException e)
			{
				
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
				threads.remove(this);
			}
			catch(ClassNotFoundException e)
			{
				System.err.println("PlayerThread: Class Not Found");
				threads.remove(this);
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
		new Server().start();
	}
}

