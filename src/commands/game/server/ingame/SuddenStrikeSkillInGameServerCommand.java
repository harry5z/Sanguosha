package commands.game.server.ingame;

import java.util.Set;

import cards.Card;
import cards.equipments.Equipment;
import core.player.PlayerCardZone;
import core.player.PlayerInfo;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractSingleStageGameController;
import core.server.game.controllers.GameController;
import core.server.game.controllers.mechanics.RecycleCardsGameController;
import core.server.game.controllers.mechanics.UnequipGameController;
import core.server.game.controllers.mechanics.UseCardOnHandGameController;
import core.server.game.controllers.specials.instants.SabotageGameController;
import exceptions.server.game.GameFlowInterruptedException;

public class SuddenStrikeSkillInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 1L;
	
	private final Card card;
	private final PlayerCardZone zone;
	private final PlayerInfo target;
	
	public SuddenStrikeSkillInGameServerCommand(Card card, PlayerCardZone zone, PlayerInfo target) {
		this.card = card;
		this.zone = zone;
		this.target = target;
	}
	
	@Override
	protected GameController getGameController() {
		return new AbstractSingleStageGameController() {
			
			@Override
			protected void handleOnce(GameInternal game) throws GameFlowInterruptedException {
				game.pushGameController(new SabotageGameController(game.getCurrentPlayer(), game.findPlayer(target)));
				if (zone == PlayerCardZone.HAND) {
					game.pushGameController(new UseCardOnHandGameController(game.getCurrentPlayer(), Set.of(card)));
				} else if (zone == PlayerCardZone.EQUIPMENT) {
					game.pushGameController(new RecycleCardsGameController(game.getCurrentPlayer(), Set.of(card)));
					game.pushGameController(new UnequipGameController(game.getCurrentPlayer(), ((Equipment) card).getEquipmentType()));
				}
			}
		};
	}

}
