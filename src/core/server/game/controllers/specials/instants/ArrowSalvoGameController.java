package core.server.game.controllers.specials.instants;

import cards.Card;
import cards.basics.Dodge;
import core.event.game.basic.RequestDodgeEvent;
import core.event.game.instants.AOETargetEffectivenessEvent;
import core.event.game.instants.ArrowSalvoTargetEffectivenessEvent;
import core.player.PlayerInfo;
import core.server.GameRoom;
import core.server.game.Damage;
import core.server.game.controllers.DamageGameController;
import core.server.game.controllers.interfaces.DodgeUsableGameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.InvalidPlayerCommandException;

public class ArrowSalvoGameController extends AOEInstantSpecialGameController implements DodgeUsableGameController {

	private boolean effective;
	private boolean hasReacted;

	public ArrowSalvoGameController(PlayerInfo source, GameRoom room) {
		super(source, room, false);
		this.effective = true;
		this.hasReacted = false;
	}

	@Override
	protected void takeEffect() {
		if (!this.hasReacted) {
			try {
				this.game.emit(new RequestDodgeEvent(this.currentTarget.getPlayerInfo()));
			} catch (GameFlowInterruptedException e) {
				// Do nothing
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
		return new ArrowSalvoTargetEffectivenessEvent(this.currentTarget, this);
	}

	@Override
	public void onDodgeUsed(Card card) {
		try {
			if (!(card instanceof Dodge) || !this.currentTarget.getCardsOnHand().contains(card)) {
				throw new InvalidPlayerCommandException("Card is not dodge or target does not have this card");
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
	public void onDodgeNotUsed() {
		this.hasReacted = true;
		this.proceed();
	}

}
