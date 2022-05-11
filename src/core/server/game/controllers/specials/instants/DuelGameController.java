package core.server.game.controllers.specials.instants;

import cards.Card;
import core.event.game.basic.RequestAttackEvent;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.game.Damage;
import core.server.game.Game;
import core.server.game.controllers.AttackUsableGameController;
import core.server.game.controllers.mechanics.DamageGameController;
import exceptions.server.game.GameFlowInterruptedException;

public class DuelGameController extends SingleTargetInstantSpecialGameController implements AttackUsableGameController {
	
	private PlayerCompleteServer currentAttackUser;
	
	public DuelGameController(PlayerInfo source, PlayerInfo target, Game game) {
		super(source, target, game);
		this.currentAttackUser = this.target;
	}

	@Override
	protected void takeEffect() throws GameFlowInterruptedException {
		// Ask current attack user to use Attack
		this.game.emit(new RequestAttackEvent(
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
	public void onAttackUsed(Card card) {
		// change attack user and continue
		this.currentAttackUser = this.currentAttackUser == this.target ? this.source : this.target;
	}

	@Override
	public void onAttackNotUsed() {
		this.nextStage();
		// current attack user takes 1 damage from the other player
		PlayerCompleteServer damageTarget = this.currentAttackUser;
		PlayerCompleteServer damageSource = damageTarget == this.target ? this.source : this.target;
		this.game.pushGameController(new DamageGameController(new Damage(damageSource, damageTarget), this.game));
	}

}
