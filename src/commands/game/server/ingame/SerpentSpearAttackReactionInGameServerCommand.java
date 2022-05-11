package commands.game.server.ingame;

import java.util.Set;

import cards.Card;
import cards.Card.Color;
import cards.basics.Attack;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.game.Game;
import core.server.game.controllers.AttackUsableGameController;
import core.server.game.controllers.mechanics.UseCardOnHandGameController;
import exceptions.server.game.InvalidPlayerCommandException;

public class SerpentSpearAttackReactionInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 1L;

	private final PlayerInfo source;
	private final Set<Card> cards;
	
	public SerpentSpearAttackReactionInGameServerCommand(PlayerInfo source, Set<Card> cards) {
		this.source = source;
		this.cards = cards;
	}
	
	@Override
	public void execute(Game game) {
		try {
			PlayerCompleteServer player = game.findPlayer(source);
			if (this.cards.size() != 2) {
				throw new InvalidPlayerCommandException("SerpentSpear requires 2 cards");
			}
			Color color = this.cards.stream().map(card -> card.getColor()).reduce(
				this.cards.iterator().next().getColor(),
				(c1, c2) -> c1 == c2 ? c1 : Color.COLORLESS
			);
			game.<AttackUsableGameController>getGameController().onAttackUsed(new Attack(color));
			game.pushGameController(new UseCardOnHandGameController(game, player, cards));
		} catch (InvalidPlayerCommandException e) {
			// TODO handle error
			e.printStackTrace();
		}
	}

}
