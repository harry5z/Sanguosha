package commands.game.server.ingame;

import java.util.Set;

import cards.Card;
import cards.equipments.Equipment;
import core.player.PlayerCardZone;
import core.player.PlayerInfo;
import core.server.game.Game;
import core.server.game.controllers.mechanics.RecycleCardsGameController;
import core.server.game.controllers.mechanics.UnequipGameController;
import core.server.game.controllers.mechanics.UseCardOnHandGameController;
import core.server.game.controllers.specials.instants.SabotageGameController;

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
	public void execute(Game game) {
		PlayerInfo source = game.getCurrentPlayer().getPlayerInfo();
		game.pushGameController(new SabotageGameController(source, this.target, game));
		if (this.zone == PlayerCardZone.HAND) {
			game.pushGameController(new UseCardOnHandGameController(game, game.getCurrentPlayer(), Set.of(card)));
		} else if (this.zone == PlayerCardZone.EQUIPMENT) {
			game.pushGameController(new RecycleCardsGameController(game, game.getCurrentPlayer(), Set.of(card)));
			game.pushGameController(new UnequipGameController(game, game.getCurrentPlayer(), ((Equipment) card).getEquipmentType()));
		}
	}

}
