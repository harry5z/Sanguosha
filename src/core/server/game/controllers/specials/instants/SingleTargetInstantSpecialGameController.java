package core.server.game.controllers.specials.instants;

import commands.game.client.RequestNeutralizationGameUIClientCommand;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.GameRoom;

public abstract class SingleTargetInstantSpecialGameController extends AbstractInstantSpecialGameController {
	
	protected PlayerCompleteServer target;

	public SingleTargetInstantSpecialGameController(PlayerInfo source, GameRoom room) {
		this(source, source, room);
	}
	
	public SingleTargetInstantSpecialGameController(PlayerInfo source, PlayerInfo target, GameRoom room) {
		super(source, room);
		this.target = game.findPlayer(target);
	}

	@Override
	public final void proceed() {
		// TODO: server side sanity check
		switch(this.stage) {
			case TARGET_LOCKED:
				this.stage = this.stage.nextStage();
				this.proceed();
				break;
			case NEUTRALIZATION:
				this.room.sendCommandToAllPlayers(new RequestNeutralizationGameUIClientCommand());
				break;
			case EFFECT:
				if (!this.neutralized) {
					this.takeEffect();
				} else {
					this.stage = this.stage.nextStage();
					this.proceed();
				}
				break;
			case EFFECT_TAKEN:
				this.game.popGameController();
				this.game.getGameController().proceed();
				break;
		}
	}
	
	protected abstract void takeEffect();

}
