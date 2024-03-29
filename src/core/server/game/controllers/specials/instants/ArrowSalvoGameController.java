package core.server.game.controllers.specials.instants;

import java.util.Queue;

import core.event.game.instants.AOETargetEffectivenessEvent;
import core.event.game.instants.ArrowSalvoTargetEffectivenessEvent;
import core.player.PlayerCompleteServer;
import core.server.game.BattleLog;
import core.server.game.Damage;
import core.server.game.GameInternal;
import core.server.game.controllers.DodgeUsableGameController;
import core.server.game.controllers.mechanics.DamageGameController;
import core.server.game.controllers.mechanics.DodgeGameController;
import exceptions.server.game.GameFlowInterruptedException;

public class ArrowSalvoGameController extends AbstractMultiTargetInstantSpecialGameController implements DodgeUsableGameController {

	private boolean effective;
	private boolean hasReacted;

	public ArrowSalvoGameController(PlayerCompleteServer source, Queue<PlayerCompleteServer> targets) {
		super(source, targets);
		this.effective = true;
		this.hasReacted = false;
	}

	@Override
	protected void takeEffect(GameInternal game) throws GameFlowInterruptedException {
		if (!this.hasReacted) {
			game.pushGameController(new DodgeGameController(
				this,
				this.currentTarget,
				this.source + " used Arrow Salvo on you, use Dodge to counter?"
			));
		} else {
			this.nextStage();
			this.hasReacted = false;
			if (this.effective) {
				// if effective, deal damage
				game.pushGameController(new DamageGameController(new Damage(this.source, this.currentTarget)));
			} else {
				game.log(BattleLog.playerADidX(currentTarget, "Dodged Arrow Salvo"));
			}
		}
	}
	
	@Override
	protected String getNullificationMessage() {
		return this.source + " used Arrow Salvo on " + this.currentTarget + ", use Nullification?";
	}
	
	@Override
	protected AOETargetEffectivenessEvent getTargetEffectivenessEvent() {
		return new ArrowSalvoTargetEffectivenessEvent(this.currentTarget, this);
	}

	@Override
	public void onDodged() {
		// mark it not effective for the current target
		this.hasReacted = true;
		this.effective = false;
	}

	@Override
	public void onNotDodged() {
		this.hasReacted = true;
		this.effective = true;
	}

}
