package core.server.game.controllers.specials.instants;

import java.util.Set;

import cards.Card;
import cards.basics.Attack;
import cards.equipments.Equipment.EquipmentType;
import cards.equipments.weapons.Weapon;
import core.event.game.basic.RequestAttackEvent;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.game.Game;
import core.server.game.controllers.AttackUsableGameController;
import core.server.game.controllers.mechanics.AttackGameController;
import core.server.game.controllers.mechanics.ReceiveCardsGameController;
import core.server.game.controllers.mechanics.UnequipGameController;
import exceptions.server.game.GameFlowInterruptedException;

public class BorrowSwordGameController
	extends SingleTargetInstantSpecialGameController
	implements AttackUsableGameController {
	
	private final PlayerCompleteServer attackTarget;
	private boolean actionTaken;

	public BorrowSwordGameController(PlayerInfo source, PlayerInfo target, PlayerInfo attackTarget, Game game) {
		super(source, target, game);
		this.attackTarget = game.findPlayer(attackTarget);
		this.actionTaken = false;
	}
	
	@Override
	protected void takeEffect() {
		if (this.actionTaken) {
			this.onUnloaded();
			this.game.getGameController().proceed();
		} else {
			try {
				this.game.emit(new RequestAttackEvent(
					this.target.getPlayerInfo(),
					"Use Attack on " + this.attackTarget + " or else " + this.source + " takes your weapon"
				));
			} catch (GameFlowInterruptedException e) {
				// should not receive GameFlowInterruptedException
			}
		}
	}

	@Override
	protected String getNeutralizationMessage() {
		return this.source + " used Borrow Sword on " + this.target + ", use Neutralization?";
	}

	@Override
	public void onAttackUsed(Card card) {
		this.actionTaken = true;
		this.game.pushGameController(new AttackGameController(this.target, this.attackTarget, (Attack) card, this.game));
	}

	@Override
	public void onAttackNotUsed() {
		this.actionTaken = true;
		Weapon weapon = this.target.getWeapon();
		this.game.pushGameController(
			new UnequipGameController(this.game, this.target, EquipmentType.WEAPON)
				.setNextController(new ReceiveCardsGameController(this.game, this.source, Set.of(weapon)))
		);
	}

}
