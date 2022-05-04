package commands.game.server.ingame;

import java.util.Set;
import java.util.stream.Collectors;

import cards.Card;
import cards.Card.Color;
import cards.basics.Attack;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.game.Game;
import core.server.game.controllers.AttackGameController;
import core.server.game.controllers.UseCardOnHandGameController;
import exceptions.server.game.InvalidPlayerCommandException;

public class SerpentSpearInitiateAttackInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 1L;

	private final PlayerInfo source;
	private final Set<PlayerInfo> targets;
	private final Set<Card> cards;
	
	public SerpentSpearInitiateAttackInGameServerCommand(PlayerInfo source, Set<PlayerInfo> targets, Set<Card> cards) {
		this.source = source;
		this.targets = targets;
		this.cards = cards;
	}
	
	@Override
	public void execute(Game game) {
		try {
			PlayerCompleteServer player = game.findPlayer(source);
			Set<PlayerCompleteServer> targets = this.targets.stream().map(target -> game.findPlayer(target)).collect(Collectors.toSet());
			player.useAttack(targets);
	
			if (this.cards.size() != 2) {
				throw new InvalidPlayerCommandException("SerpentSpear requires 2 cards");
			}
			Color color = this.cards.stream().map(card -> card.getColor()).reduce(
				this.cards.iterator().next().getColor(),
				(c1, c2) -> c1 == c2 ? c1 : Color.COLORLESS
			);
			game.pushGameController(new AttackGameController(player, targets, new Attack(color), game));
			game.pushGameController(new UseCardOnHandGameController(game, player, cards));
			game.getGameController().proceed();
		} catch (InvalidPlayerCommandException e) {
			// TODO handle error
			e.printStackTrace();
		}
	}

}
