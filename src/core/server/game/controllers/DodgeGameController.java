package core.server.game.controllers;

import cards.Card;
import cards.basics.Dodge;
import core.event.game.DodgeArbitrationEvent;
import core.event.game.basic.RequestDodgeEvent;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import core.server.game.controllers.interfaces.DodgeUsableGameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.InvalidPlayerCommandException;
import utils.EnumWithNextStage;

public class DodgeGameController extends AbstractGameController {
	
	public enum DodgeStage implements EnumWithNextStage<DodgeStage> {
		ARIBTRATION,
		DODGE,
		AFTER_DODGED_SKILLS,
		END,
	}
	
	private DodgeStage stage;
	private PlayerCompleteServer target;
	private boolean dodged;

	public DodgeGameController(Game game, PlayerCompleteServer target) {
		super(game);
		this.target = target;
		this.stage = DodgeStage.ARIBTRATION;
		this.dodged = false;
	}

	@Override
	public void proceed() {
		switch (this.stage) {
			case ARIBTRATION:
				try {
					this.game.emit(new DodgeArbitrationEvent(this.target.getPlayerInfo()));
					this.stage = this.stage.nextStage();
					this.proceed();
				} catch (GameFlowInterruptedException e) {
					e.resume();
				}
				break;
			case DODGE:
				try {
					this.game.emit(new RequestDodgeEvent(this.target.getPlayerInfo()));
				} catch (GameFlowInterruptedException e) {
					e.resume();
				}
				break;
			case AFTER_DODGED_SKILLS:
				this.stage = this.stage.nextStage();
				this.proceed();
				break;
			case END:
				this.onUnloaded();
				this.game.getGameController().proceed();
				break;
		}
	}

	public void onDodgeUsed(Card card) {
		try {
			if (!(card instanceof Dodge) || !this.target.getCardsOnHand().contains(card)) {
				throw new InvalidPlayerCommandException("Card is not dodge or target does not have this card");
			}
			this.target.useCard(card);
		} catch (InvalidPlayerCommandException e) {
			e.printStackTrace();
			return;
		}
		this.dodged = true;
		this.stage = DodgeStage.AFTER_DODGED_SKILLS;
	}
	

	public void onDodgeNotUsed() {
		this.dodged = false;
		this.stage = DodgeStage.END;
	}

	public void onDodgeStageSkipped() {
		this.dodged = true;
		this.stage = DodgeStage.AFTER_DODGED_SKILLS;
	}

	public void onDodgeStageNotSkipped() {
		this.dodged = false;
		this.stage = DodgeStage.DODGE;
	}
	
	@Override
	protected void onNextControllerLoaded(GameController controller) {
		if (this.dodged) {
			((DodgeUsableGameController) controller).onDodged();
		} else {
			((DodgeUsableGameController) controller).onNotDodged();
		}
	}

}
