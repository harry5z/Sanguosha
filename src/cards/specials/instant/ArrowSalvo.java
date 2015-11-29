package cards.specials.instant;

import player.PlayerComplete;

import commands.Command;
import commands.operations.special.ArrowSalvoOperation;
import core.client.game.operations.Operation;

public class ArrowSalvo extends Instant {

	private static final long serialVersionUID = -1395738598490175305L;

	public ArrowSalvo(int num, Suit suit) {
		super(num, suit);
	}

	@Override
	public String getName() {
		return "Arrow Salvo";
	}

}
