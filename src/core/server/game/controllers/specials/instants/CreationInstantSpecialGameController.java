package core.server.game.controllers.specials.instants;

import commands.game.client.RequestNeutralizationGameUIClientCommand;
import core.player.PlayerInfo;
import core.server.GameRoom;

public class CreationInstantSpecialGameController extends AbstractInstantSpecialGameController {

	public CreationInstantSpecialGameController(PlayerInfo source, GameRoom room) {
		super(source, room);
	}
	
	@Override
	public void proceed() {
		switch(stage) {
			case TARGET_LOCKED:
				stage = stage.nextStage();
				proceed();
				break;
			case NEUTRALIZATION:
				room.sendCommandToAllPlayers(new RequestNeutralizationGameUIClientCommand());
				break;
			case EFFECT:
				if (!neutralized) {
					game.drawCards(source, 2);
				}
				stage = stage.nextStage();
				proceed();
				break;
			case EFFECT_TAKEN:
				source.clearDisposalArea();
				game.popGameController();
				game.getGameController().proceed();
				break;
		}
	}

}
