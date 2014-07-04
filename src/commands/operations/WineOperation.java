package commands.operations;

import commands.Command;
import commands.IncreaseOfHealth;
import commands.UseOfCards;
import player.PlayerComplete;
import player.PlayerOriginal;
import cards.Card;
import core.Game;
import core.PlayerInfo;

/**
 * Operation of use of wine. Wine can be used either to increase attack damage once,
 * or used to save oneself when dying
 * @author Harry
 *
 */
public class WineOperation extends Operation
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3919164368836659196L;
	private PlayerInfo source;
	private Card wine;
	public WineOperation(PlayerInfo player, Card wine, Command next)
	{
		super(next);
		source = player;
		this.wine = wine;
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
		player.getGameListener().setCardSelected(wine, false);
	}

	@Override
	public void onConfirmedBy(PlayerComplete player) 
	{
		player.getGameListener().setCardSelected(wine, false);
		player.getGameListener().setCancelEnabled(false);
		if(player.isDying())//can increase health by 1
		{
			player.sendToServer(new UseOfCards(source,wine,new IncreaseOfHealth(source,getNext())));
		}
		else//can increase attack damage by 1
		{
			player.useWine();
			player.sendToServer(new UseOfCards(source,wine,getNext()));
		}
	}

}
