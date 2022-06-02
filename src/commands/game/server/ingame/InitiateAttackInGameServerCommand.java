package commands.game.server.ingame;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import cards.basics.Attack;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.game.BattleLog;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractSingleStageGameController;
import core.server.game.controllers.GameController;
import core.server.game.controllers.mechanics.AttackGameController;
import core.server.game.controllers.mechanics.UseCardOnHandGameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.IllegalPlayerActionException;
import exceptions.server.game.InvalidCardException;
import exceptions.server.game.InvalidPlayerCommandException;

public class InitiateAttackInGameServerCommand extends InGameServerCommand {
	
	private static final long serialVersionUID = -4460787768760646177L;

	private final Set<PlayerInfo> targets;
	private Attack attack;
	
	public InitiateAttackInGameServerCommand(Set<PlayerInfo> targets, Attack attack) {
		this.targets = targets;
		this.attack = attack;
	}

	@Override
	public GameController getGameController() {
		return new AbstractSingleStageGameController() {
			
			@Override
			protected void handleOnce(GameInternal game) throws GameFlowInterruptedException {
				try {
					Set<PlayerCompleteServer> set = targets.stream().map(target -> game.findPlayer(target)).collect(Collectors.toSet());
					source.useAttack();
					game.pushGameController(new AttackGameController(source, set, attack));
					game.pushGameController(new UseCardOnHandGameController(source, Set.of(attack)));
					game.log(BattleLog.playerADidX(source, "used Attack").onPlayers(set).withCard(attack));
				} catch (InvalidPlayerCommandException e) {
					// TODO reset game state
					e.printStackTrace();
				}
			}
		};
	}

	@Override
	public void validate(GameInternal game) throws IllegalPlayerActionException {
		if (source.getAttackUsed() >= source.getAttackLimit()) {
			throw new IllegalPlayerActionException("Attack: Player may no longer attack this turn");
		}
		
		if (attack == null || targets == null || targets.isEmpty()) {
			throw new IllegalPlayerActionException("Attack: Card/Targets cannot be null");
		}
		
		try {
			attack = (Attack) game.getDeck().getValidatedCard(attack);
		} catch (InvalidCardException e) {
			throw new IllegalPlayerActionException("Attack: Card is invalid");
		} catch (ClassCastException e) {
			throw new IllegalPlayerActionException("Attack: Card is not an Attack");
		}

		if (!source.getCardsOnHand().contains(attack)) {
			throw new IllegalPlayerActionException("Attack: Player does not own the card used");
		}
		
		if (targets.size() > source.getAttackTargetLimit()) {
			throw new IllegalPlayerActionException("Attack: Too many targets");
		}
		
		Set<PlayerCompleteServer> tgts = new HashSet<>();
		for (PlayerInfo target : targets) {
			PlayerCompleteServer other = game.findPlayer(target);
			if (other == null) {
				throw new IllegalPlayerActionException("Attack: Target not found");
			}
			if (source.equals(other)) {
				throw new IllegalPlayerActionException("Attack: Target cannot be oneself");
			}
			if (!source.isPlayerInAttackRange(other, game.getNumberOfPlayersAlive())) {
				throw new IllegalPlayerActionException("Attack: Target not in attack range");
			}
			if (!other.isAlive()) {
				throw new IllegalPlayerActionException("Attack: Target not alive");
			}
			tgts.add(other);
		}
		
		if (tgts.size() < targets.size()) {
			throw new IllegalPlayerActionException("Attack: Cannot have duplicated targets");
		}
	}

}
