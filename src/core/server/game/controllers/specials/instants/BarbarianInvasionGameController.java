package core.server.game.controllers.specials.instants;

import cards.Card;
import cards.basics.Attack;
import core.event.game.basic.RequestAttackEvent;
import core.event.game.instants.AOETargetEffectivenessEvent;
import core.event.game.instants.BarbarianInvasionTargetEffectivenessEvent;
import core.player.PlayerInfo;
import core.server.GameRoom;
import core.server.game.Damage;
import core.server.game.controllers.DamageGameController;
import core.server.game.controllers.interfaces.AttackUsableGameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.InvalidPlayerCommandException;

public class BarbarianInvasionGameController extends AOEInstantSpecialGameController implements AttackUsableGameController {
	
	private boolean effective;
	private boolean hasReacted;

	public BarbarianInvasionGameController(PlayerInfo source, GameRoom room) {
		super(source, room, false);
		this.effective = true;
		this.hasReacted = false;
	}

	@Override
	protected void takeEffect() {
		if (!this.hasReacted) {
			try {
				this.game.emit(new RequestAttackEvent(this.currentTarget.getPlayerInfo()));
			} catch (GameFlowInterruptedException e) {
				e.resume();
			}
		} else {
			this.stage = this.stage.nextStage();
			this.hasReacted = false;
			if (this.effective) {
				// if effective, deal damage
				this.effective = true;
				this.game.pushGameController(new DamageGameController(new Damage(this.source, this.currentTarget), this.game));
				this.game.getGameController().proceed();
			} else {
				// otherwise proceed to next
				this.effective = true;
				this.proceed();
			}
		}
	}

	@Override
	protected AOETargetEffectivenessEvent getTargetEffectivenessEvent() {
		return new BarbarianInvasionTargetEffectivenessEvent(this.currentTarget, this);
	}

	@Override
	public void onAttackUsed(Card card) {
		try {
			if (!(card instanceof Attack) || !this.currentTarget.getCardsOnHand().contains(card)) {
				throw new InvalidPlayerCommandException("Card is not attack or target does not have this card");
			}
			this.currentTarget.useCard(card);
		} catch (InvalidPlayerCommandException e) {
			e.printStackTrace();
			return;
		}
		// mark it not effective for the current target
		this.hasReacted = true;
		this.effective = false;
		this.proceed();
	}

	@Override
	public void onAttackNotUsed() {
		this.hasReacted = true;
		this.proceed();
	}

}
