package cards.specials.instant;

import commands.server.ingame.InGameServerCommand;
import commands.server.ingame.InitiateFireAttackInGameServerCommand;
import core.client.game.operations.Operation;
import core.client.game.operations.instants.FireAttackOperation;
import ui.game.interfaces.Activatable;

public class FireAttack extends Instant {
	private static final long serialVersionUID = -1L;
	public static final String FIRE_ATTACK = "Fire Attack";

	public FireAttack(int num, Suit suit, int id) {
		super(num, suit, id);
	}

	@Override
	public String getName() {
		return FIRE_ATTACK;
	}

	@Override
	public Operation generateOperation(Activatable source) {
		return new FireAttackOperation(source);
	}

	@Override
	public Class<? extends InGameServerCommand> getAllowedDealPhaseResponseType() {
		return InitiateFireAttackInGameServerCommand.class;
	}
}
