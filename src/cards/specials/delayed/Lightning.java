package cards.specials.delayed;

import core.client.ClientGameInfo;
import core.client.game.operations.Operation;

public class Lightning extends Delayed {

	private static final long serialVersionUID = -8502796453884676779L;

	public Lightning(int num, Suit suit, int id) {
		super(num, suit, id);
	}

	@Override
	public String getName() {
		return "Lightning";
	}

	@Override
	public boolean isActivatable(ClientGameInfo game) {
		// if(player.canBeTargetedBy(this);
		return true;
	}

	@Override
	public Operation generateOperation() {
		// TODO Auto-generated method stub
		return null;
	}

}
