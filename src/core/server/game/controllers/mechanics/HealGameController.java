package core.server.game.controllers.mechanics;

import core.player.PlayerCompleteServer;
import core.server.game.BattleLog;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractGameController;
import core.server.game.controllers.GameControllerStage;
import exceptions.server.game.GameFlowInterruptedException;

public final class HealGameController extends AbstractGameController<HealGameController.HealStage> {
	
	public static enum HealStage implements GameControllerStage<HealStage> {
		HEAL_VALUE,
		HEAL,
		AFTER_HEAL,
		END;
	}
	
	private PlayerCompleteServer source;
	private PlayerCompleteServer target;
	private int value;

	public HealGameController(PlayerCompleteServer source, PlayerCompleteServer target) {
		this(source, target, 1);
	}
	
	public HealGameController(PlayerCompleteServer source, PlayerCompleteServer target, int value) {
		this.source = source;
		this.target = target;
		this.value = value;
	}
	
	public void addHealValue(int value) {
		this.value += value;
	}

	@Override
	protected void handleStage(GameInternal game, HealStage stage) throws GameFlowInterruptedException {
		switch(stage) {
			case HEAL_VALUE:
				// nothing here yet
				this.nextStage();
				break;
			case HEAL:
				this.target.changeHealthCurrentBy(this.value);
				game.log(BattleLog.playerADidX(target, "HP <b>+" + value + "</b>"));
				this.nextStage();
				break;
			case AFTER_HEAL:
				// nothing here yet
				this.nextStage();
				break;
			case END:
				break;
		}
	}

	@Override
	protected HealStage getInitialStage() {
		return HealStage.HEAL_VALUE;
	}

}
