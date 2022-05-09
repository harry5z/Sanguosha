package core.server.game.controllers.mechanics;

import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.game.Game;
import core.server.game.controllers.AbstractGameController;
import utils.EnumWithNextStage;

public final class HealGameController extends AbstractGameController {
	
	public static enum HealStage implements EnumWithNextStage<HealStage> {
		HEAL_VALUE,
		HEAL,
		AFTER_HEAL,
		END;
	}
	
	private PlayerCompleteServer source;
	private PlayerCompleteServer target;
	private int value;
	private HealStage stage;

	public HealGameController(PlayerInfo source, PlayerInfo target, Game game) {
		this(source, target, game, 1);
	}
	
	public HealGameController(PlayerInfo source, PlayerInfo target, Game game, int value) {
		super(game);
		this.source = game.findPlayer(source);
		this.target = game.findPlayer(target);
		this.stage = HealStage.HEAL_VALUE;
		this.value = value;
	}
	
	public void addHealValue(int value) {
		this.value += value;
	}

	@Override
	public void proceed() {
		switch(this.stage) {
			case HEAL_VALUE:
				this.stage = this.stage.nextStage();
				this.proceed();
				break;
			case HEAL:
				this.target.changeHealthCurrentBy(this.value);
				this.stage = this.stage.nextStage();
				this.proceed();
				break;
			case AFTER_HEAL:
				this.stage = this.stage.nextStage();
				this.proceed();
				break;
			case END:
				this.onUnloaded();
				this.game.getGameController().proceed();
				break;
		}
	}
}
