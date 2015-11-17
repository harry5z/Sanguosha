package commands.operations.special;

import commands.Command;
import commands.DrawCardsFromDeck;
import commands.game.server.UseOfCardsInGameServerCommand;
import cards.Card;
import player.PlayerOriginal;
import player.PlayerComplete;
import core.PlayerInfo;

public class CreationOperation extends SpecialOperation
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1410334098472333831L;
	private PlayerInfo source;
	private Card creation;
	
	public CreationOperation(PlayerInfo source, PlayerInfo turnPlayer, Card creation, Command next)
	{
		super(next, turnPlayer);
		this.source = source;
		this.creation = creation;
	}

	@Override
	public void onPlayerSelected(PlayerComplete operator,	PlayerOriginal player) 
	{
		//No player selection
	}

	@Override
	public void onCardSelected(PlayerComplete operator, Card card) 
	{
		//No card selection process
	}

	@Override
	public void onCancelledBy(PlayerComplete player) 
	{
		player.setConfirmEnabled(false);
		player.setCancelEnabled(false);
		player.setCardOnHandSelected(creation, false);	
	}

	@Override
	public void onConfirmedBy(PlayerComplete player) 
	{
		player.setOperation(null);
		player.getGameListener().setCardOnHandSelected(creation, false);
		player.getGameListener().setCancelEnabled(false);
		player.sendToServer(new UseOfCardsInGameServerCommand(source,creation,this));
	}

	@Override
	protected void playerOpEffect(PlayerComplete player) 
	{
		if(player.equals(source))
		{
			this.nextStage();
			player.sendToServer(new DrawCardsFromDeck(player.getPlayerInfo(),2,this));
		}
	}

	@Override
	public String getName() 
	{
		return "Creation";
	}

	@Override
	public PlayerInfo getCurrentTarget() 
	{
		return source;
	}
}
