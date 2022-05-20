package commands.game.server.ingame;

import cards.Card;
import cards.equipments.Equipment.EquipmentType;
import cards.specials.instant.BorrowSword;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.game.GameInternal;
import core.server.game.controllers.GameController;
import core.server.game.controllers.specials.instants.BorrowSwordGameController;
import exceptions.server.game.IllegalPlayerActionException;

public class InitiateBorrowSwordInGameServerCommand extends AbstractInitiationInGameServerCommand {

	private static final long serialVersionUID = 1L;
	private final PlayerInfo attackTarget;

	public InitiateBorrowSwordInGameServerCommand(PlayerInfo target, PlayerInfo attackTarget, Card card) {
		super(target, card);
		this.attackTarget = attackTarget;
	}

	@Override
	protected GameController getInitiationGameController(GameInternal game, PlayerCompleteServer target) {
		return new BorrowSwordGameController(game.getCurrentPlayer(), target, game.findPlayer(attackTarget));
	}

	@Override
	protected void validateCardType(GameInternal game) throws IllegalPlayerActionException {
		if (!(card instanceof BorrowSword)) {
			throw new IllegalPlayerActionException("Borrow Sword: Card is not a BorrowSword");
		}
	}

	@Override
	protected void validateTarget(GameInternal game) throws IllegalPlayerActionException {
		PlayerCompleteServer tgt = game.findPlayer(target);
		PlayerCompleteServer atkTarget = game.findPlayer(attackTarget);
		
		if (tgt == null || atkTarget == null) {
			throw new IllegalPlayerActionException("Borrow Sword: Target not found");
		}
		
		if (source.equals(tgt)) {
			throw new IllegalPlayerActionException("Borrow Sword: Target cannot be oneself");
		}
		
		if (!tgt.isAlive()) {
			throw new IllegalPlayerActionException("Borrow Sword: Target not alive");
		}
		
		if (!atkTarget.isAlive()) {
			throw new IllegalPlayerActionException("Borrow Sword: Attack target not alive");
		}
		
		if (!tgt.isEquipped(EquipmentType.WEAPON)) {
			throw new IllegalPlayerActionException("Borrow Sword: Target does not have a Weapon");
		}
		
		if (tgt.equals(atkTarget)) {
			throw new IllegalPlayerActionException("Borrow Sword: Attack target cannot be oneself");
		}
		
		if (!tgt.isPlayerInAttackRange(atkTarget, game.getNumberOfPlayersAlive())) {
			throw new IllegalPlayerActionException("Borrow Sword: Attack target not in attack range");
		}
		
	}

}
