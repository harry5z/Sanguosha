package core.server;

import listeners.game.HealthListener;
import net.server.Server;
import player.PlayerComplete;

import commands.GraphicsUpdate;

public class HealthUpdater extends GraphicsUpdater implements HealthListener
{
	public HealthUpdater(Server server, PlayerComplete player)
	{
		super(server,player);
	}
	@Override
	public void onSetHealthLimit(final int limit) 
	{
		server.sendToAllClients(new GraphicsUpdate()
		{
			private static final long serialVersionUID = -8885352709691065126L;
			@Override
			public void ClientOperation(PlayerComplete player) 
			{
				player.findMatch(info).changeHealthLimitTo(limit);
			}
		});
	}

	@Override
	public void onSetHealthCurrent(final int current)
	{
		server.sendToAllClients(new GraphicsUpdate()
		{
			private static final long serialVersionUID = -168269510804574820L;
			@Override
			public void ClientOperation(PlayerComplete player) 
			{
				player.findMatch(info).changeHealthCurrentTo(current);
			}
		});
	}

	@Override
	public void onHealthChangedBy(final int amount) 
	{
		server.sendToAllClients(new GraphicsUpdate()
		{
			private static final long serialVersionUID = 1584844782673599792L;
			@Override
			public void ClientOperation(PlayerComplete player) 
			{
				player.findMatch(info).changeHealthCurrentBy(amount);
			}
		});
	}

	@Override
	public void onDeath() {
		// TODO Auto-generated method stub
		
	}

}
