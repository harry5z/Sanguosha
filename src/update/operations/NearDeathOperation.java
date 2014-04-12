package update.operations;

import player.PlayerClientComplete;
import player.PlayerOriginal;
import update.DeathEvent;
import update.Update;
import cards.Card;
import cards.basics.Peach;
import cards.basics.Wine;
import core.Framework;
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
	
	public NearDeathOperation(PlayerInfo current,PlayerInfo killer,PlayerInfo dying,Update next)
	{
		super(next);
		this.killer = killer;
		turnPlayer = current;
		currentPlayer = current;
		dyingPlayer = dying;
		stage = BEFORE;
	}
	private void sendToNextPlayer(PlayerClientComplete player)
	{
		currentPlayer = player.getNextPlayerAlive();
		if(currentPlayer.getPosition() == turnPlayer.getPosition())//circle complete
		{
			stage++;
		}
		player.sendToMaster(this);
	}
	@Override
	public void frameworkOperation(Framework framework) 
	{
		framework.sendToAllClients(this);
	}

	@Override
	public void playerOperation(PlayerClientComplete player) 
	{
		System.out.println(player.getName()+" NearDeathEvent "+stage);
		if(player.matches(currentPlayer) && !player.findMatch(dyingPlayer).isDying())//dying player saved
		{
			player.sendToMaster(getNext());//continue the game
		}
		else if(stage == BEFORE)//for future skills
		{
			if(player.matches(currentPlayer))
			{
				sendToNextPlayer(player);
			}
			return;
		}
		else if(stage == DURING)//asking for peach
		{
			if(player.matches(currentPlayer))
			{
				player.setOperation(this);//push to operation
				player.getGameListener().setCancelEnabled(true);//can refuse to give peach
				player.setCardSelectableByName(Peach.PEACH, true);//peach enabled
				player.getGameListener().setMessage(dyingPlayer.getName() + " is dying, do you use a peach?");
				if(player.matches(dyingPlayer))//if dying player himself
				{
					player.setCardSelectableByName(Wine.WINE, true);//wine also usable
					player.getGameListener().setMessage("You are dying, do you use wine or peach?");
				}
			}
			return;
		}
		else if(stage == AFTER)//no one saves
		{
			if(player.matches(turnPlayer))
			{
				player.sendToMaster(new DeathEvent(turnPlayer,killer,dyingPlayer,getNext()));
			}
		}
	}

	@Override
	public void onPlayerSelected(PlayerClientComplete operator,PlayerOriginal player) 
	{
		//no player selection in this event
	}

	@Override
	public void onCardSelected(PlayerClientComplete operator, Card card) 
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
	public void onCancelledBy(PlayerClientComplete player) 
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
	public void onConfirmedBy(PlayerClientComplete player) 
	{
		cardChosen = null;
		Operation temp = operation;
		operation = null;
		temp.onConfirmedBy(player);
	}

}
