package commands.game.server.ingame;

import java.util.Set;
import java.util.stream.Collectors;

import cards.basics.Attack;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.game.Game;
import core.server.game.controllers.AttackGameController;
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
		try {
			player.useAttack();
		} catch (InvalidPlayerCommandException e) {
			e.printStackTrace();
			return;
		}
		if (attack != null) {
			try {
				player.useCard(attack);
			} catch (InvalidPlayerCommandException e) {
				try {
					player.setAttackUsed(player.getAttackUsed() - 1);
				} catch (InvalidPlayerCommandException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
				return;
			}
		}
		game.pushGameController(new AttackGameController(
			player,
			this.targets.stream().map(target -> game.findPlayer(target)).collect(Collectors.toSet()),
			attack,
			game
		));
		game.getGameController().proceed();
	}

}
