package core.event.handlers.hero;

import cards.Card.Color;
import cards.basics.Attack;
import core.event.game.basic.AttackLockedTargetSkillsCheckEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.ConnectionController;
import core.server.game.Game;
import core.server.game.controllers.AttackResolutionGameController.AttackResolutionStage;
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
	protected void handleIfActivated(AttackLockedTargetSkillsCheckEvent event, Game game, ConnectionController connection)
		throws GameFlowInterruptedException {
		if (this.player != event.source) {
			return;
		}

		Attack card = event.controller.getAttackCard();
		
		if (card.getColor() == Color.BLACK) {
			throw new GameFlowInterruptedException(() -> {
				event.controller.setStage(AttackResolutionStage.END);
				event.controller.proceed();
			});
		}
				

	}

}
