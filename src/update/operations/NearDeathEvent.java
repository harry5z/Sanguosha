package update.operations;

import basics.Peach;
import basics.Wine;
import player.PlayerOriginal;
import player.PlayerOriginalClientComplete;
import update.Update;
import core.Card;
import core.Framework;
import core.Operation;
import core.PlayerInfo;

/**
 * The near-death event, has high priority and is invoked immediately after a player's
 * health drops below 1
 * @author Harry
 *
 */
public class NearDeathEvent implements Operation
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
	private Update next;
	private int stage;
	private Operation operation;
	private Card cardChosen;
	
	public NearDeathEvent(PlayerInfo current,PlayerInfo killer,PlayerInfo dying,Update next)
	{
		this.killer = killer;
		turnPlayer = current;
		currentPlayer = current;
		dyingPlayer = dying;
		this.next = next;
		stage = BEFORE;
	}
	private void sendToNextPlayer(PlayerOriginalClientComplete player)
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
	public void playerOperation(PlayerOriginalClientComplete player) 
	{
		System.out.println(player.getName()+" NearDeathEvent "+stage);
		if(player.matches(currentPlayer) && !player.findMatch(dyingPlayer).isDying())//dying player saved
		{
			player.sendToMaster(next);//continue the game
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
				player.setCancelEnabled(true);//can refuse to give peach
				player.setCardSelectableByName(Peach.PEACH, true);//peach enabled
				if(player.matches(dyingPlayer))//if dying player himself
				{
					player.setCardSelectableByName(Wine.WINE, true);//wine also usable
				}
				
			}
			return;
		}
		else if(stage == AFTER)//no one saves
		{
			if(player.matches(turnPlayer))
			{
				stage = END;
				player.sendToMaster(new DeathEvent(turnPlayer,killer,dyingPlayer,this));
			}
		}
		else if(stage == END)
		{
			if(player.matches(turnPlayer))
				player.sendToMaster(next);
		}
	}

	@Override
	public void onPlayerSelected(PlayerOriginalClientComplete operator,PlayerOriginal player) 
	{
		//no player selection in this event
	}

	@Override
	public void onCardSelected(PlayerOriginalClientComplete operator, Card card) 
	{
		if(operation == null)//ready to use card
		{
			cardChosen = card;
			operation = card.onActivatedBy(operator, this);
			if(cardChosen.getName().equals(Peach.PEACH))
			{
				((PeachOperation)operation).setTarget(dyingPlayer);
			}
		
			operator.setConfirmEnabled(true);
		}
		else
		{
			operation.onCancelledBy(operator);
			if(cardChosen.equals(card))//cancel selection
			{
				operation = null;
				cardChosen = null;
				operator.setCancelEnabled(true);
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
	public void onCancelledBy(PlayerOriginalClientComplete player) 
	{
		if(operation == null)//refuse to give peach
		{
			player.setCancelEnabled(false);
			player.setAllCardsOnHandSelectable(false);
			sendToNextPlayer(player);
		}
		else//cancel operation
		{
			cardChosen = null;
			operation.onCancelledBy(player);
			operation = null;
			player.setCancelEnabled(true);
			player.setOperation(this);//push back to operation
		}
	}

	@Override
	public void onConfirmedBy(PlayerOriginalClientComplete player) 
	{
		cardChosen = null;
		Operation temp = operation;
		operation = null;
		temp.onConfirmedBy(player);
	}

}
