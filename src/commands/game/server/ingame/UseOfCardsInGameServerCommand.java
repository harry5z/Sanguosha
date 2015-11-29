package commands.game.server.ingame;

import java.util.Set;

import cards.Card;
import core.PlayerInfo;

/**
 * use of cards by a player, used cards are displayed in the disposal area until an event
 * is finished, then they are recycled by deck
 * @author Harry
 *
 */
public abstract class UseOfCardsInGameServerCommand extends InGameServerCommand
{
	private static final long serialVersionUID = -6281985550611404675L;

	private PlayerInfo source;
	private final Set<Card> cardsUsed;
	
	public UseOfCardsInGameServerCommand(PlayerInfo player, Set<Card> cards) {
		this.source = player;
		this.cardsUsed = cards;
	}

	public void setSource(PlayerInfo player) {
		this.source = player;
	}
	
	public PlayerInfo getSource() {
		return source;
	}
	
	public Set<Card> getCardsUsed() {
		return cardsUsed;
	}

}
