package core.server.game.controllers.specials.instants;

import cards.Card;
import commands.game.client.RequestAttackGameUIClientCommand;
import core.player.PlayerCompleteServer;
import core.server.game.Damage;
import core.server.game.GameInternal;
import core.server.game.controllers.AttackUsableGameController;
import core.server.game.controllers.mechanics.DamageGameController;
import exceptions.server.game.GameFlowInterruptedException;

public class DuelGameController extends SingleTargetInstantSpecialGameController implements AttackUsableGameController {
	
	private PlayerCompleteServer currentAttackUser;
	
	public DuelGameController(PlayerCompleteServer source, PlayerCompleteServer target) {
		super(source, target);
		this.currentAttackUser = this.target;
	}

	@Override
	protected void takeEffect(GameInternal game) throws GameFlowInterruptedException {
		// Ask current attack user to use Attack
		game.getConnectionController().sendCommandToAllPlayers(
			new RequestAttackGameUIClientCommand(
				this.currentAttackUser.getPlayerInfo(),
				"Duel: it is your turn to use Attack."
			)
		);
		throw new GameFlowInterruptedException();
	}
	

	@Override
	protected String getNullificationMessage() {
		return this.source + " used Duel on " + this.target + ", use Nullification?";
	}

	@Override
	public void onAttackUsed(GameInternal game, Card card) {
		// change attack user and continue
		this.currentAttackUser = this.currentAttackUser == this.target ? this.source : this.target;
	}

	@Override
	public void onAttackNotUsed(GameInternal game) {
		this.nextStage();
		// current attack user takes 1 damage from the other player
		PlayerCompleteServer damageTarget = this.currentAttackUser;
		PlayerCompleteServer damageSource = damageTarget == this.target ? this.source : this.target;
		game.pushGameController(new DamageGameController(new Damage(damageSource, damageTarget)));
	}

}
