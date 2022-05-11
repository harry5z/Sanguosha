package core.server.game.controllers.specials.instants;

import core.player.PlayerInfo;
import core.server.game.Game;

public class CreationGameController extends SingleTargetInstantSpecialGameController {

	public CreationGameController(PlayerInfo source, Game game) {
		super(source, source, game);
	}

	@Override
	protected void takeEffect() {
		this.game.drawCards(this.source, 2);
		this.nextStage();
	}
	
	@Override
	protected String getNeutralizationMessage() {
		return this.source + " used Creation, use Neutralization?";
	}

}
