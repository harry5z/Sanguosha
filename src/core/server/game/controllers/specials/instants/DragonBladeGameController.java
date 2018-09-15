package core.server.game.controllers.specials.instants;

import cards.Card;
import cards.basics.Attack;
import core.event.game.basic.RequestAttackEvent;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import core.server.game.controllers.AbstractGameController;
import core.server.game.controllers.AttackGameController;
import core.server.game.controllers.interfaces.AttackUsableGameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.InvalidPlayerCommandException;

public class DragonBladeGameController extends AbstractGameController implements AttackUsableGameController {
	
	private final PlayerCompleteServer source;
	private final PlayerCompleteServer target;
	private boolean actionTaken;

	public DragonBladeGameController(Game game, PlayerCompleteServer source, PlayerCompleteServer target) {
		super(game);
		this.source = source;
		this.target = target;
		this.actionTaken = false;
	}

	@Override
	public void proceed() {
		if (this.actionTaken) {
			this.onUnloaded();
			this.game.getGameController().proceed();
		} else {
			try {
				this.game.emit(new RequestAttackEvent(
					this.source.getPlayerInfo(),
					"Use Dragon Blade?"
				));
			} catch (GameFlowInterruptedException e) {
				e.resume();
			}
		}
	}

	@Override
	public void onAttackUsed(Card card) {
		this.actionTaken = true;
		try {
			this.source.useCard(card);
			this.game.pushGameController(new AttackGameController(this.source, this.target, (Attack) card, this.game));
		} catch (InvalidPlayerCommandException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onAttackNotUsed() {
		this.actionTaken = true;
	}

}
