package core.event.handlers.hero;

import core.event.game.damage.SourceHeroOnDamageEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.game.GameDriver;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractSingleStageGameController;
import core.server.game.controllers.mechanics.HealGameController;
import exceptions.server.game.GameFlowInterruptedException;

public class WeiYanRampageOnDamageEventHandler extends AbstractEventHandler<SourceHeroOnDamageEvent> {

	public WeiYanRampageOnDamageEventHandler(PlayerCompleteServer player) {
		super(player);
	}

	@Override
	public Class<SourceHeroOnDamageEvent> getEventClass() {
		return SourceHeroOnDamageEvent.class;
	}

	@Override
	protected void handleIfActivated(SourceHeroOnDamageEvent event, GameDriver game) throws GameFlowInterruptedException {
		if (this.player != event.getDamage().getSource()) {
			return;
		}
		
		game.pushGameController(new AbstractSingleStageGameController() {
			@Override
			protected void handleOnce(GameInternal game) throws GameFlowInterruptedException {
				if (!player.isDamaged()) {
					// can't heal if source player at full health
					return;
				}
				
				if (player.isPlayerInDistance(event.getDamage().getTarget(), game.getNumberOfPlayersAlive())) {
					// heal up to the same as damage
					int healAmount = Math.min(player.getHealthLimit() - player.getHealthCurrent(), event.getDamage().getAmount());
					game.pushGameController(new HealGameController(player, player, healAmount));
				}
			}
		});

	}

}
