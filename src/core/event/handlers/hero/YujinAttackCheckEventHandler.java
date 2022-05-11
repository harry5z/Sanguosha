package core.event.handlers.hero;

import cards.Card.Color;
import core.event.game.basic.AttackLockedTargetSkillsCheckEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import core.server.game.controllers.AbstractSingleStageGameController;
import core.server.game.controllers.mechanics.AttackResolutionGameController.AttackResolutionStage;
import exceptions.server.game.GameFlowInterruptedException;

public class YujinAttackCheckEventHandler extends AbstractEventHandler<AttackLockedTargetSkillsCheckEvent> {

	public YujinAttackCheckEventHandler(PlayerCompleteServer player) {
		super(player);
	}

	@Override
	public Class<AttackLockedTargetSkillsCheckEvent> getEventClass() {
		return AttackLockedTargetSkillsCheckEvent.class;
	}

	@Override
	protected void handleIfActivated(AttackLockedTargetSkillsCheckEvent event, Game game)
		throws GameFlowInterruptedException {
		if (this.player != event.source) {
			return;
		}
		
		if (event.controller.getAttackCard().getColor() == Color.BLACK) {
			game.pushGameController(new AbstractSingleStageGameController(game) {
				@Override
				protected void handleOnce() throws GameFlowInterruptedException {
					// skip Attack Resolution
					event.controller.setStage(AttackResolutionStage.END);
				}
			});
		}

	}

}
