package commands.game.server.ingame;

import java.util.Set;
import java.util.stream.Collectors;

import cards.basics.Attack;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.game.Game;
import core.server.game.controllers.mechanics.AttackGameController;
import core.server.game.controllers.mechanics.UseCardOnHandGameController;
import exceptions.server.game.InvalidPlayerCommandException;

public class InitiateAttackInGameServerCommand extends InGameServerCommand {
	
	private static final long serialVersionUID = -4460787768760646177L;

	private final PlayerInfo source;
	private final Set<PlayerInfo> targets;
	private final Attack attack;
	
	public InitiateAttackInGameServerCommand(PlayerInfo source, Set<PlayerInfo> targets, Attack attack) {
		this.source = source;
		this.targets = targets;
		this.attack = attack;
	}

	@Override
	public void execute(Game game) {
		PlayerCompleteServer player = game.findPlayer(source);
		Set<PlayerCompleteServer> targets = this.targets.stream().map(target -> game.findPlayer(target)).collect(Collectors.toSet());
		try {
			player.useAttack(targets);
			game.pushGameController(new AttackGameController(player, targets, attack, game));
			if (attack != null) {
				game.pushGameController(new UseCardOnHandGameController(game, player, Set.of(this.attack)));
			}
			game.getGameController().proceed();
		} catch (InvalidPlayerCommandException e) {
			// TODO reset game state
			e.printStackTrace();
			return;
		}
	}

}
