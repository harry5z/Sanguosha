package core.client.game.operations.basics;

import cards.Card;
import cards.basics.Attack;
import commands.game.server.ingame.AttackReactionInGameServerCommand;
import commands.game.server.ingame.InGameServerCommand;
import core.client.game.event.AttackReactionClientGameEvent;
import core.client.game.operations.AbstractCardReactionOperation;

public class AttackReactionOperation extends AbstractCardReactionOperation {

	public AttackReactionOperation(String message) {
		super(message);
	}

	@Override
	protected boolean isCardActivatable(Card card) {
		return card instanceof Attack;
	}
	
	@Override
	protected boolean isCancelAllowed() {
		return true;
	}
	
	@Override
	public void onLoadedCustom() {
		this.panel.emit(new AttackReactionClientGameEvent(true));
	}
	
	@Override
	public void onUnloadedCustom() {
		this.panel.emit(new AttackReactionClientGameEvent(false));
	}

	@Override
	protected InGameServerCommand getCommand(Card card) {
		return new AttackReactionInGameServerCommand(this.panel.getGameState().getSelf().getPlayerInfo(), card);
	}
	
}
