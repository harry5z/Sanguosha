package core.event.handlers.hero;

import cards.Card.Color;
import cards.basics.Attack;
import core.event.game.basic.AttackOnLockSkillsCheckEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.ConnectionController;
import core.server.game.Game;
import core.server.game.controllers.AttackGameController.AttackStage;
import exceptions.server.game.GameFlowInterruptedException;

public class YujinAttackCheckEventHandler extends AbstractEventHandler<AttackOnLockSkillsCheckEvent> {

	public YujinAttackCheckEventHandler(PlayerCompleteServer player) {
		super(player);
	}

	@Override
	public Class<AttackOnLockSkillsCheckEvent> getEventClass() {
		return AttackOnLockSkillsCheckEvent.class;
	}

	@Override
	protected void handleIfActivated(AttackOnLockSkillsCheckEvent event, Game game, ConnectionController connection)
		throws GameFlowInterruptedException {
		if (this.player != event.source) {
			return;
		}

		Attack card = event.controller.getAttackCard();
		
		if (card.getColor() == Color.BLACK) {
			throw new GameFlowInterruptedException(() -> {
				event.controller.setStage(AttackStage.END);
				event.controller.proceed();
			});
		}
				

	}

}
