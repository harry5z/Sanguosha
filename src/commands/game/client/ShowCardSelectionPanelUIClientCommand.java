package commands.game.client;

import java.util.Collection;

import core.client.GamePanel;
import core.client.game.operations.PlayerCardSelectionOperation;
import core.heroes.Hero;
import core.player.PlayerCardZone;
import core.player.PlayerInfo;

public class ShowCardSelectionPanelUIClientCommand extends GeneralGameUIClientCommand {

	private static final long serialVersionUID = 1L;
	
	private final PlayerInfo source;
	private final PlayerInfo target;
	private final Collection<PlayerCardZone> zones;
	
	public ShowCardSelectionPanelUIClientCommand(PlayerInfo source, PlayerInfo target, Collection<PlayerCardZone> zones) {
		this.source = source;
		this.target = target;
		this.zones = zones;
	}


	@Override
	protected void execute(GamePanel<? extends Hero> panel) {
		if (panel.getContent().getSelf().getPlayerInfo().equals(this.source)) {
			panel.pushOperation(new PlayerCardSelectionOperation(this.target, this.zones));
		} else {
			panel.getContent().getOtherPlayerUI(this.source).showCountdownBar();
		}
	}

}
