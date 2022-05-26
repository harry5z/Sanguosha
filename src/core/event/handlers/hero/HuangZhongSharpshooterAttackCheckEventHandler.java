package core.event.handlers.hero;

import core.event.game.basic.AttackLockedSourceSkillsCheckEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.game.GameDriver;
import core.server.game.controllers.skills.HuangZhongSharpshooterGameController;
import exceptions.server.game.GameFlowInterruptedException;

public class HuangZhongSharpshooterAttackCheckEventHandler extends AbstractEventHandler<AttackLockedSourceSkillsCheckEvent> {
	
	public HuangZhongSharpshooterAttackCheckEventHandler(PlayerCompleteServer player) {
		super(player);
	}

	@Override
	public Class<AttackLockedSourceSkillsCheckEvent> getEventClass() {
		return AttackLockedSourceSkillsCheckEvent.class;
	}

	@Override
	protected void handleIfActivated(AttackLockedSourceSkillsCheckEvent event, GameDriver game) throws GameFlowInterruptedException {
		if (this.player != event.source) {
			return;
		}
		
		if (event.target.getHandCount() >= player.getHealthCurrent() || event.target.getHandCount() <= player.getAttackRange()) {
			game.pushGameController(new HuangZhongSharpshooterGameController(event.controller, player));
		}
	}
}
