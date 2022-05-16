package commands.game.client.sync.delayed;

import cards.Card;
import commands.game.client.sync.AbstractSyncGameUIClientCommand;
import core.client.GamePanel;
import utils.DelayedType;

public class SyncDelayedAddedGameUIClientCommand extends AbstractSyncGameUIClientCommand {
	
	private static final long serialVersionUID = 1L;
	
	private final String name;
	private final Card card;
	private final DelayedType type;
	private final boolean add;
	
	public SyncDelayedAddedGameUIClientCommand(String name, Card card, DelayedType type, boolean add) {
		this.name = name;
		this.card = card;
		this.type = type;
		this.add = add;
	}

	@Override
	protected void execute(GamePanel panel) {
		if (panel.getGameState().getSelf().getName().equals(this.name)) {
			if (this.add) {
				panel.getGameState().getSelf().pushDelayed(this.card, this.type);
			} else {
				panel.getGameState().getSelf().removeDelayed(this.type);
			}
		} else {
			if (this.add) {
				panel.getGameState().getPlayer(this.name).pushDelayed(this.card, this.type);
			} else {
				panel.getGameState().getPlayer(this.name).removeDelayed(this.type);
			}
		}
	}

}
