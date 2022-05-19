package core.server.game.controllers.specials;

import commands.game.client.RequestNullificationGameUIClientCommand;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractGameController;
import core.server.game.controllers.GameControllerStage;
import exceptions.server.game.GameFlowInterruptedException;

public abstract class AbstractSpecialGameController<T extends GameControllerStage<?>> extends AbstractGameController<T> implements SpecialGameController {
	
	protected boolean nullified;
	protected int nullifiedCount;

	public AbstractSpecialGameController() {
		this.nullified = false;
		this.nullifiedCount = 0;
	}

	@Override
	public final void onNullified() {
		this.nullified = !this.nullified;
		this.nullifiedCount = 0;
	}
	
	@Override
	public final void onNullificationCanceled() {
		this.nullifiedCount++;
	}
	
	@Override
	public final void onNullificationTimeout() {
		// put some arbitrary large number to exceed number of players
		this.nullifiedCount = 10000;
	}
	
	protected final void handleNullificationStage(GameInternal game) throws GameFlowInterruptedException {
		if (this.nullifiedCount >= game.getNumberOfPlayersAlive()) {
			this.nullifiedCount = 0;
			this.nextStage();
		} else {
			if (this.nullifiedCount == 0) {
				throw new GameFlowInterruptedException(
					new RequestNullificationGameUIClientCommand(getNullificationMessage())
				);
			} else {
				throw new GameFlowInterruptedException(null);
			}
		}
	}
	
	protected abstract String getNullificationMessage();

}
