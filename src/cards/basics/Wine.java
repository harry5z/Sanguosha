package cards.basics;

import commands.server.ingame.InGameServerCommand;
import commands.server.ingame.UseWineInGameServerCommand;
import core.GameState;
import core.client.game.operations.Operation;
import core.client.game.operations.basics.WineOperation;
import core.player.PlayerCompleteClient;
import ui.game.interfaces.Activatable;

public class Wine extends Basic {

	private static final long serialVersionUID = 8731935586533627689L;

	public static final String WINE = "Wine";

	public Wine(int num, Suit suit, int id) {
		super(num, suit, id);
	}

	@Override
	public String getName() {
		return WINE;
	}

	@Override
	public boolean isActivatable(GameState game) {
		PlayerCompleteClient player = game.getSelf();
		return player.getWineUsed() < player.getWineLimit() && !player.isWineUsed();
	}

	@Override
	public Operation generateOperation(Activatable source) {
		return new WineOperation(source);
	}

	@Override
	public Class<? extends InGameServerCommand> getAllowedDealPhaseResponseType() {
		return UseWineInGameServerCommand.class;
	}
}
