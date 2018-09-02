package commands.game.client;

import java.util.Collection;

import core.client.game.operations.Operation;
import core.client.game.operations.PlayerCardSelectionOperation;
import core.player.PlayerCardZone;
import core.player.PlayerInfo;

public class ShowCardSelectionPanelUIClientCommand extends AbstractSingleTargetOperationGameClientCommand {

	private static final long serialVersionUID = 1L;
	
	private final PlayerInfo selectionTarget;
	private final Collection<PlayerCardZone> zones;
	
	public ShowCardSelectionPanelUIClientCommand(PlayerInfo source, PlayerInfo selectionTarget, Collection<PlayerCardZone> zones) {
		super(source);
		this.selectionTarget = selectionTarget;
		this.zones = zones;
	}

	@Override
	protected Operation getOperation() {
		return new PlayerCardSelectionOperation(this.selectionTarget, this.zones);
	}

}
