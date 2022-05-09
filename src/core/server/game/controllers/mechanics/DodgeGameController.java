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
import core.server.game.controllers.GameControllerStage;
import exceptions.server.game.GameFlowInterruptedException;

public class DodgeGameController extends AbstractGameController<DodgeGameController.DodgeStage> {
	
	public enum DodgeStage implements GameControllerStage<DodgeStage> {
		TARGET_EQUIPMENT_ABILITIES,
		DODGE,
		AFTER_DODGED_SKILLS,
		END,
	}
	
	private final DodgeUsableGameController nextController;
	private String message;
	private PlayerCompleteServer target;
	private final Set<DodgeStage> skippedStages;

	public DodgeGameController(Game game, DodgeUsableGameController nextController, PlayerCompleteServer target, String message) {
		super(game);
		this.nextController = nextController;
		this.message = message;
		this.target = target;
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
					this.game.emit(new DodgeTargetEquipmentCheckEvent(this, this.target.getPlayerInfo()));
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
		this.nextController.onDodged();
		this.stage = DodgeStage.AFTER_DODGED_SKILLS;
	}
	

	public void onDodgeNotUsed() {
		this.nextController.onNotDodged();
		this.stage = DodgeStage.END;
	}

	public void onDodgeStageSkipped() {
		this.nextController.onDodged();
		this.stage = DodgeStage.AFTER_DODGED_SKILLS;
	}

	public void onDodgeStageNotSkipped() {
		this.nextController.onNotDodged();
		this.stage = DodgeStage.DODGE;
	}

	public void skipStage(DodgeStage stage) {
		this.skippedStages.add(stage);
	}

	@Override
	protected DodgeStage getInitialStage() {
		return DodgeStage.TARGET_EQUIPMENT_ABILITIES;
	}

}
