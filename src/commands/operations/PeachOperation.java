package commands.operations;

import player.PlayerComplete;
import player.PlayerOriginal;
import cards.Card;

import commands.Command;
import commands.IncreaseOfHealth;
import commands.game.server.UseOfCardsInGameServerCommand;
import core.Game;
import core.PlayerInfo;

/**
 * Operation of use of peach
 * @author Harry
 *
 */
public class PeachOperation extends Operation
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1723063004362265957L;
	private PlayerInfo source;
	private PlayerInfo target;
	private Card peach;
	public PeachOperation(PlayerInfo player, Card peach, Command next)
	{
		super(new IncreaseOfHealth(player,next));
		source = player;
		target = null;
		this.peach = peach;
	}
	/**
	 * used when saving others during near-death event
	 * @param player
	 */
	public void setTarget(PlayerInfo player)
	{
		target = player;
		((IncreaseOfHealth)getNext()).setTarget(target);
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
		player.getGameListener().setConfirmEnabled(false);
		player.getGameListener().setCancelEnabled(false);
		player.getGameListener().setCardSelected(peach, false);
	}

	@Override
	public void onConfirmedBy(PlayerComplete player) 
	{
		player.setOperation(null);
		player.getGameListener().setCardSelected(peach, false);
		player.getGameListener().setCancelEnabled(false);
		player.sendToServer(new UseOfCardsInGameServerCommand(source,peach,getNext()));
	}

}
