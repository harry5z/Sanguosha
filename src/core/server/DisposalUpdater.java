package core.server;

import net.server.Server;
import commands.GraphicsUpdate;
import listeners.CardDisposalListener;
import player.PlayerComplete;
import cards.Card;

public class DisposalUpdater extends GraphicsUpdater implements CardDisposalListener
{
	public DisposalUpdater(Server server, PlayerComplete player) 
	{
		super(server, player);
	}

	@Override
	public void onCardUsed(final Card card) 
	{
		server.sendToAllClients(new GraphicsUpdate()
		{
			private static final long serialVersionUID = 613975596899900804L;
			@Override
			public void ClientOperation(PlayerComplete player)
			{
				player.findMatch(info).getDisposalListener().onCardUsed(card);
			}
		});
	}

	@Override
	public void onCardDisposed(final Card card)
	{
		server.sendToAllClients(new GraphicsUpdate()
		{
			private static final long serialVersionUID = 2023560779024907658L;
			@Override
			public void ClientOperation(PlayerComplete player)
			{
				player.findMatch(info).getDisposalListener().onCardDisposed(card);
			}
		});
		
	}

	@Override
	public void refresh() 
	{
		server.sendToAllClients(new GraphicsUpdate()
		{
			private static final long serialVersionUID = -6582557054043737928L;
			@Override
			public void ClientOperation(PlayerComplete player)
			{
				player.findMatch(info).getDisposalListener().refresh();
			}
		});
	}

}
