package core.server.game.controllers.specials.instants;

import java.util.Queue;

import cards.Card;
import core.event.game.basic.RequestAttackEvent;
import core.event.game.instants.AOETargetEffectivenessEvent;
import core.event.game.instants.BarbarianInvasionTargetEffectivenessEvent;
import core.player.PlayerCompleteServer;
import core.server.game.Damage;
import core.server.game.Game;
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
	protected void takeEffect(Game game) throws GameFlowInterruptedException {
		if (!this.hasReacted) {
			game.emit(new RequestAttackEvent(
				this.currentTarget.getPlayerInfo(),
				this.source + " used Barbarian Invasion on you, use Attack to counter?"
			));
			throw new GameFlowInterruptedException();
		} else {
			this.nextStage();
			this.hasReacted = false;
			if (this.effective) {
				// if effective, deal damage
				game.pushGameController(new DamageGameController(new Damage(this.source, this.currentTarget)));
			}
		}
	}
	
	@Override
	protected String getNeutralizationMessage() {
		return this.source + " used Barbarian Invasion on " + this.currentTarget + ", use Neutralization?";
	}

	@Override
	protected AOETargetEffectivenessEvent getTargetEffectivenessEvent() {
		return new BarbarianInvasionTargetEffectivenessEvent(this.currentTarget, this);
	}

	@Override
	public void onAttackUsed(Game game, Card card) {
		// mark it not effective for the current target
		this.hasReacted = true;
		this.effective = false;
	}

	@Override
	public void onAttackNotUsed(Game game) {
		this.hasReacted = true;
		this.effective = true;
	}

}
