package core.server.game.controllers.specials.instants;

import java.util.Set;

import cards.Card;
import cards.basics.Attack;
import cards.equipments.Equipment.EquipmentType;
import cards.equipments.weapons.Weapon;
import commands.game.client.RequestAttackGameUIClientCommand;
import core.player.PlayerCompleteServer;
import core.server.game.GameInternal;
import core.server.game.controllers.AttackUsableGameController;
import core.server.game.controllers.mechanics.AttackGameController;
import core.server.game.controllers.mechanics.ReceiveCardsGameController;
import core.server.game.controllers.mechanics.UnequipGameController;
import exceptions.server.game.GameFlowInterruptedException;

public class BorrowSwordGameController
	extends SingleTargetInstantSpecialGameController
	implements AttackUsableGameController {
	
	private final PlayerCompleteServer attackTarget;

	public BorrowSwordGameController(PlayerCompleteServer source, PlayerCompleteServer target, PlayerCompleteServer attackTarget) {
		super(source, target);
		this.attackTarget = attackTarget;
	}
	
	@Override
	protected void takeEffect(GameInternal game) throws GameFlowInterruptedException {
		// depending on the rule, may also need to check attack range at this point
		if (!this.target.isEquipped(EquipmentType.WEAPON)) {
			this.nextStage();
			return;
		}

		throw new GameFlowInterruptedException(
			new RequestAttackGameUIClientCommand(
				this.target,
				"Use Attack on " + this.attackTarget + " or else " + this.source + " takes your weapon"
			)
		);
	}

	@Override
	protected String getNullificationMessage() {
		return this.source + " used Borrow Sword on " + this.target + ", use Nullification?";
	}

	@Override
	public void onAttackUsed(GameInternal game, Card card) {
		this.nextStage();
		game.pushGameController(new AttackGameController(this.target, this.attackTarget, (Attack) card));
	}

	@Override
	public void onAttackNotUsed(GameInternal game) {
		this.nextStage();
		Weapon weapon = this.target.getWeapon();
		game.pushGameController(new ReceiveCardsGameController(this.source, Set.of(weapon)));
		game.pushGameController(new UnequipGameController(this.target, EquipmentType.WEAPON));
	}

}
