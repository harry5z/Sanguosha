package core.server.game.controllers.specials.instants;

import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.game.Game;

public abstract class AbstractInstantSpecialGameController implements InstantSpecialGameController {

	protected SpecialStage stage;
	protected PlayerCompleteServer source;
	protected final Game game;
	protected boolean neutralized;
	protected int neutralizedCount;
	
	public AbstractInstantSpecialGameController(PlayerInfo source, Game game) {
		this.stage = SpecialStage.TARGET_LOCKED;
		this.game = game;
		this.source = game.findPlayer(source);
		this.neutralized = false;
		this.neutralizedCount = 0;
	}
	
	@Override
	public void onNeutralized() {
		this.neutralized = !this.neutralized;
		this.neutralizedCount = 0;
		this.proceed();
	}
	
	@Override
	public void onNeutralizationCanceled() {
		this.neutralizedCount++;
		// WARNING: may need another initiator if some player died during neutralization check
		if (this.neutralizedCount >= this.game.getNumberOfPlayersAlive()) {
			this.neutralizedCount = 0;
			this.stage = this.stage.nextStage();
			this.proceed();
		}
	}
	
}
