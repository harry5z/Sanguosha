package commands.operations;

import commands.Command;
import commands.DeathEvent;
import player.PlayerComplete;
import player.PlayerOriginal;
import cards.Card;
import cards.basics.Peach;
import cards.basics.Wine;
import core.Game;
import core.PlayerInfo;

/**
 * The near-death event, has high priority and is invoked immediately after a player's
 * health drops below 1
 * @author Harry
 *
 */
public class NearDeathOperation extends Operation
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7812190809416225897L;
	public static final int BEFORE = 1;
	public static final int DURING = 2;
	public static final int AFTER = 3;
	public static final int END = 4;
	private PlayerInfo turnPlayer;//player playing this turn
	private PlayerInfo currentPlayer;//player currently being asked for peach
	private PlayerInfo killer;
	private PlayerInfo dyingPlayer;//player dying
	private int stage;
	private Operation operation;
	private Card cardChosen;
	
	public NearDeathOperation(PlayerInfo current,PlayerInfo killer,PlayerInfo dying,Command next)
	{
		super(next);
		this.killer = killer;
		turnPlayer = current;
		currentPlayer = current;
		dyingPlayer = dying;
		stage = BEFORE;
	}
	private void sendToNextPlayer(PlayerComplete player)
	{
		currentPlayer = player.getNextPlayerAlive();
		if(currentPlayer.getPosition() == turnPlayer.getPosition())//circle complete
		{
			stage++;
		}
		player.sendToServer(this);
	}
	@Override
	public void ServerOperation(Game framework) 
	{
		framework.sendToAllClients(this);
	}

	@Override
	public void ClientOperation(PlayerComplete player) 
	{
		System.out.println(player.getName()+" NearDeathEvent "+stage);
		if(player.equals(currentPlayer) && !player.findMatch(dyingPlayer).isDying())//dying player saved
		{
			player.sendToServer(getNext());//continue the game
		}
		else if(stage == BEFORE)//for future skills
		{
			if(player.equals(currentPlayer))
			{
				sendToNextPlayer(player);
			}
			return;
		}
		else if(stage == DURING)//asking for peach
		{
			if(player.equals(currentPlayer))
			{
				player.setOperation(this);//push to operation
				player.getGameListener().setCancelEnabled(true);//can refuse to give peach
				player.setCardSelectableByName(Peach.PEACH, true);//peach enabled
				player.getGameListener().setMessage(dyingPlayer.getName() + " is dying, do you use a peach?");
				if(player.equals(dyingPlayer))//if dying player himself
				{
					player.setCardSelectableByName(Wine.WINE, true);//wine also usable
					player.getGameListener().setMessage("You are dying, do you use wine or peach?");
				}
			}
			return;
		}
		else if(stage == AFTER)//no one saves
		{
			if(player.equals(turnPlayer))
			{
				player.sendToServer(new DeathEvent(turnPlayer,killer,dyingPlayer,getNext()));
			}
		}
	}

	@Override
	public void onPlayerSelected(PlayerComplete operator,PlayerOriginal player) 
	{
		//no player selection in this event
	}

	@Override
	public void onCardSelected(PlayerComplete operator, Card card) 
	{
		if(operation == null)//ready to use card
		{
			cardChosen = card;
			operation = card.onActivatedBy(operator, this);
			if(cardChosen instanceof Peach)
			{
				((PeachOperation)operation).setTarget(dyingPlayer);
			}
		
			operator.getGameListener().setConfirmEnabled(true);
		}
		else
		{
			operation.onCancelledBy(operator);
			if(cardChosen.equals(card))//cancel selection
			{
				operation = null;
				cardChosen = null;
				operator.getGameListener().setCancelEnabled(true);
			}
			else //change selection
			{
				cardChosen = card;
				operation = card.onActivatedBy(operator, this);
				if(cardChosen.getName().equals(Peach.PEACH))
					((PeachOperation)operation).setTarget(dyingPlayer);
			}
		}
	}

	@Override
	public void onCancelledBy(PlayerComplete player) 
	{
		if(operation == null)//refuse to give peach
		{
			player.getGameListener().setCancelEnabled(false);
			player.setAllCardsOnHandSelectable(false);
			sendToNextPlayer(player);
		}
		else//cancel operation
		{
			cardChosen = null;
			operation.onCancelledBy(player);
			operation = null;
			player.getGameListener().setCancelEnabled(true);
			player.setOperation(this);//push back to operation
		}
	}

	@Override
	public void onConfirmedBy(PlayerComplete player) 
	{
		cardChosen = null;
		Operation temp = operation;
		operation = null;
		temp.onConfirmedBy(player);
	}

}
