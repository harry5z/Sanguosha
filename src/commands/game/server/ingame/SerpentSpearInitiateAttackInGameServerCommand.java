package commands.game.server.ingame;

import java.util.Set;
import java.util.stream.Collectors;

import cards.Card;
import cards.Card.Color;
import cards.basics.Attack;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractSingleStageGameController;
import core.server.game.controllers.GameController;
import core.server.game.controllers.mechanics.AttackGameController;
import core.server.game.controllers.mechanics.UseCardOnHandGameController;
import exceptions.server.game.GameFlowInterruptedException;
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
	protected GameController getGameController() {
		return new AbstractSingleStageGameController() {
			
			@Override
			protected void handleOnce(GameInternal game) throws GameFlowInterruptedException {
				try {
					PlayerCompleteServer player = game.findPlayer(source);
					Set<PlayerCompleteServer> set = targets.stream().map(target -> game.findPlayer(target)).collect(Collectors.toSet());
					player.useAttack(set);
			
					if (cards.size() != 2) {
						throw new InvalidPlayerCommandException("SerpentSpear requires 2 cards");
					}
					Color color = cards.stream().map(card -> card.getColor()).reduce(
						cards.iterator().next().getColor(),
						(c1, c2) -> c1 == c2 ? c1 : Color.COLORLESS
					);
					game.pushGameController(new AttackGameController(player, set, new Attack(color)));
					game.pushGameController(new UseCardOnHandGameController(player, cards));
				} catch (InvalidPlayerCommandException e) {
					// TODO handle error
					e.printStackTrace();
				}
			}
		};
	}

}
