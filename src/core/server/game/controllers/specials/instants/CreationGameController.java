package core.server.game.controllers.specials.instants;

import core.player.PlayerInfo;
import core.server.GameRoom;

public class CreationGameController extends SingleTargetInstantSpecialGameController {

	public CreationGameController(PlayerInfo source, GameRoom room) {
		super(source, room);
	}

	@Override
	protected void takeEffect() {
		this.game.drawCards(this.source, 2);
		this.stage = this.stage.nextStage();
		this.proceed();
	}

}
