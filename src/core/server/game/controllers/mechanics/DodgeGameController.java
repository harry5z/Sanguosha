package core.server.game.controllers.mechanics;

import java.util.HashSet;
import java.util.Set;

import cards.Card;
import core.event.game.DodgeTargetEquipmentCheckEvent;
import core.event.game.basic.RequestDodgeEvent;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import core.server.game.controllers.AbstractGameController;
import core.server.game.controllers.DodgeUsableGameController;
import core.server.game.controllers.GameController;
import exceptions.server.game.GameFlowInterruptedException;
import utils.EnumWithNextStage;

public class DodgeGameController extends AbstractGameController {
	
	public enum DodgeStage implements EnumWithNextStage<DodgeStage> {
		TARGET_EQUIPMENT_ABILITIES,
		DODGE,
		AFTER_DODGED_SKILLS,
		END,
	}
	
	private String message;
	private DodgeStage stage;
	private PlayerCompleteServer target;
	private boolean dodged;
	private final Set<DodgeStage> skippedStages;

	public DodgeGameController(Game game, PlayerCompleteServer target, String message) {
		super(game);
		this.message = message;
		this.target = target;
		this.stage = DodgeStage.TARGET_EQUIPMENT_ABILITIES;
		this.dodged = false;
		this.skippedStages = new HashSet<>();
	}

	@Override
	public void proceed() {
		if (this.skippedStages.contains(this.stage)) {
			this.stage = this.stage.nextStage();
			this.proceed();
			return;
		}
		switch (this.stage) {
			case TARGET_EQUIPMENT_ABILITIES:
				try {
					this.game.emit(new DodgeTargetEquipmentCheckEvent(this.target.getPlayerInfo()));
					this.stage = this.stage.nextStage();
					this.proceed();
				} catch (GameFlowInterruptedException e) {
					e.resume();
				}
				break;
			case DODGE:
				try {
					this.game.emit(new RequestDodgeEvent(this.target.getPlayerInfo(), this.message));
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
		game.pushGameController(new UseCardOnHandGameController(game, target, Set.of(card)));
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
	
	public void skipStage(DodgeStage stage) {
		this.skippedStages.add(stage);
	}

}
