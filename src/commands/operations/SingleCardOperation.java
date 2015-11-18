package commands.operations;

import player.PlayerComplete;
import player.PlayerOriginal;
import cards.Card;

import commands.Command;
import commands.game.server.UseOfCardsInGameServerCommand;
import core.PlayerInfo;
import core.client.game.operations.Operation;
import core.server.Game;

public class SingleCardOperation extends Operation
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8013540053622863152L;
	private PlayerInfo source;
	private PlayerInfo target;
	private Card card;
	
	public SingleCardOperation(PlayerInfo source, Card card,Command next)
	{
		super(next);
		this.source = source;
		this.card = card;
	}
	@Override
	public void ServerOperation(Game framework) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ClientOperation(PlayerComplete player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPlayerSelected(PlayerComplete operator,
			PlayerOriginal player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCardSelected(PlayerComplete operator, Card card) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCancelledBy(PlayerComplete player) 
	{
		player.setConfirmEnabled(false);
		player.setCancelEnabled(false);
		player.setCardOnHandSelected(card, false);	
	}

	@Override
	public void onConfirmedBy(PlayerComplete player) 
	{
		player.setOperation(null);
		player.setCardOnHandSelected(card, false);
		player.setCancelEnabled(false);
		player.sendToServer(new UseOfCardsInGameServerCommand(source,card,getNext()));		
	}

}
