package core.server;

import net.server.Server;
import commands.GraphicsUpdate;
import listeners.CardOnHandListener;
import player.PlayerComplete;
import cards.Card;

public class HandUpdater extends GraphicsUpdater implements CardOnHandListener
{
	public HandUpdater(Server server, PlayerComplete player) 
	{
		super(server, player);
	}

	@Override
	public void onCardAdded(final Card card) 
	{
		server.sendToAllClients(new GraphicsUpdate() 
		{
			private static final long serialVersionUID = -5645459444058555486L;
			@Override
			public void ClientOperation(PlayerComplete player) 
			{
				player.findMatch(info).addCard(card);
			}
		});
	}

	@Override
	public void onCardRemoved(final Card card) 
	{
		server.sendToAllClients(new GraphicsUpdate() 
		{
			private static final long serialVersionUID = -2696738616377062516L;
			@Override
			public void ClientOperation(PlayerComplete player)
			{
				player.findMatch(info).removeCardFromHand(card);
			}
		});		
	}
}
