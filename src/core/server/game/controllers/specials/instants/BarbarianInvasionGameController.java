package core.server.game.controllers.specials.instants;

import java.util.Queue;

import cards.Card;
import commands.client.game.RequestAttackGameUIClientCommand;
import core.event.game.instants.AOETargetEffectivenessEvent;
import core.event.game.instants.BarbarianInvasionTargetEffectivenessEvent;
import core.player.PlayerCompleteServer;
import core.server.game.BattleLog;
import core.server.game.Damage;
import core.server.game.GameInternal;
import core.server.game.controllers.AttackUsableGameController;
import core.server.game.controllers.mechanics.DamageGameController;
import exceptions.server.game.GameFlowInterruptedException;

public class BarbarianInvasionGameController extends AbstractMultiTargetInstantSpecialGameController implements AttackUsableGameController {
	
	private boolean effective;
	private boolean hasReacted;

	public BarbarianInvasionGameController(PlayerCompleteServer source, Queue<PlayerCompleteServer> targets) {
		super(source, targets);
		this.effective = true;
		this.hasReacted = false;
	}

	@Override
	protected void takeEffect(GameInternal game) throws GameFlowInterruptedException {
		if (!this.hasReacted) {
			throw new GameFlowInterruptedException(
				new RequestAttackGameUIClientCommand(
					this.currentTarget,
					this.source + " used Barbarian Invasion on you, use Attack to counter?"
				)
			); 
		} else {
			this.nextStage();
			this.hasReacted = false;
			if (this.effective) {
				// if effective, deal damage
				game.pushGameController(new DamageGameController(new Damage(this.source, this.currentTarget)));
			} else {
				game.log(BattleLog.playerADidX(currentTarget, "used Attack against Barbarian Invasion"));
			}
		}
	}
	
	@Override
	protected String getNullificationMessage() {
		return this.source + " used Barbarian Invasion on " + this.currentTarget + ", use Nullification?";
	}

	@Override
	protected AOETargetEffectivenessEvent getTargetEffectivenessEvent() {
		return new BarbarianInvasionTargetEffectivenessEvent(this.currentTarget, this);
	}

	@Override
	public void onAttackUsed(GameInternal game, Card card) {
		// mark it not effective for the current target
		this.hasReacted = true;
		this.effective = false;
	}

	@Override
	public void onAttackNotUsed(GameInternal game) {
		this.hasReacted = true;
		this.effective = true;
	}

}
