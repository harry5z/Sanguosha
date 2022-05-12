package core.server.game.controllers.specials.instants;

import cards.Card;
import core.event.game.basic.RequestAttackEvent;
import core.player.PlayerCompleteServer;
import core.server.game.Damage;
import core.server.game.Game;
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
	protected void takeEffect(Game game) throws GameFlowInterruptedException {
		// Ask current attack user to use Attack
		game.emit(new RequestAttackEvent(
			this.currentAttackUser.getPlayerInfo(),
			"You are in a Duel against " +
			(this.currentAttackUser == this.target ? this.source : this.target) +
			", it's your turn to use Attack."
		));
		throw new GameFlowInterruptedException();
	}
	

	@Override
	protected String getNeutralizationMessage() {
		return this.source + " used Duel on " + this.target + ", use Neutralization?";
	}

	@Override
	public void onAttackUsed(Game game, Card card) {
		// change attack user and continue
		this.currentAttackUser = this.currentAttackUser == this.target ? this.source : this.target;
	}

	@Override
	public void onAttackNotUsed(Game game) {
		this.nextStage();
		// current attack user takes 1 damage from the other player
		PlayerCompleteServer damageTarget = this.currentAttackUser;
		PlayerCompleteServer damageSource = damageTarget == this.target ? this.source : this.target;
		game.pushGameController(new DamageGameController(new Damage(damageSource, damageTarget)));
	}

}
