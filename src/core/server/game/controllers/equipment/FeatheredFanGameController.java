package core.server.game.controllers.equipment;

import cards.basics.Attack;
import core.server.game.Game;
import core.server.game.Damage.Element;
import core.server.game.controllers.AbstractGameController;
import core.server.game.controllers.DecisionRequiredGameController;
import core.server.game.controllers.mechanics.AttackGameController;

public class FeatheredFanGameController extends AbstractGameController implements DecisionRequiredGameController {
	
	private final AttackGameController attackController;

	public FeatheredFanGameController(Game game, AttackGameController attackController) {
		super(game);
		this.attackController = attackController;
	}

	@Override
	public void proceed() {
		this.onUnloaded();
		this.game.getGameController().proceed();
	}

	@Override
	public void onDecisionMade(boolean confirmed) {
		if (confirmed) {
			Attack original = this.attackController.getAttackCard();
			this.attackController.setAttackCard(new Attack(Element.FIRE, original.getNumber(), original.getSuit()));
		}
	}

}
