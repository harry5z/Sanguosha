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

	public DodgeGameController(DodgeUsableGameController nextController, PlayerCompleteServer target, String message) {
		this.nextController = nextController;
		this.message = message;
		this.target = target;
		this.skippedStages = new HashSet<>();
	}

	@Override
	protected void handleStage(Game game, DodgeStage stage) throws GameFlowInterruptedException {
		if (this.skippedStages.contains(stage)) {
			this.nextStage();
			return;
		}
		switch (stage) {
			case TARGET_EQUIPMENT_ABILITIES:
				this.nextStage();
				game.emit(new DodgeTargetEquipmentCheckEvent(this, this.target.getPlayerInfo()));
				break;
			case DODGE:
				game.emit(new RequestDodgeEvent(this.target.getPlayerInfo(), this.message));
				throw new GameFlowInterruptedException();
			case AFTER_DODGED_SKILLS:
				// nothing here yet
				this.nextStage();
				break;
			case END:
				break;
		}
	}

	public void onDodgeUsed(Game game, Card card) {
		game.pushGameController(new UseCardOnHandGameController(target, Set.of(card)));
		this.nextController.onDodged();
		this.setStage(DodgeStage.AFTER_DODGED_SKILLS);
	}
	

	public void onDodgeNotUsed() {
		this.nextController.onNotDodged();
		this.setStage(DodgeStage.END);
	}

	public void onDodgeStageSkipped() {
		this.nextController.onDodged();
		this.setStage(DodgeStage.AFTER_DODGED_SKILLS);
	}

	public void onDodgeStageNotSkipped() {
		this.nextController.onNotDodged();
		this.setStage(DodgeStage.DODGE);
	}

	public void skipStage(DodgeStage stage) {
		this.skippedStages.add(stage);
	}

	@Override
	protected DodgeStage getInitialStage() {
		return DodgeStage.TARGET_EQUIPMENT_ABILITIES;
	}

}
