package core.client.game.operations.basics;

import cards.Card;
import cards.basics.Attack;
import commands.game.server.ingame.AttackReactionInGameServerCommand;
import commands.game.server.ingame.InGameServerCommand;
import core.client.game.event.AttackReactionClientGameEvent;
import core.client.game.operations.AbstractSingleCardReactionOperation;

public class AttackReactionOperation extends AbstractSingleCardReactionOperation {

	public AttackReactionOperation(String message) {
		super(message);
	}

	@Override
	protected boolean isCardActivatable(Card card) {
		return card instanceof Attack;
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
	protected InGameServerCommand getCommandOnConfirm() {
		return new AttackReactionInGameServerCommand(
			this.panel.getGameState().getSelf().getPlayerInfo(),
			this.getFirstCardUI().getCard()
		);
	}

	@Override
	protected InGameServerCommand getCommandOnCancel() {
		return new AttackReactionInGameServerCommand(
			this.panel.getGameState().getSelf().getPlayerInfo(),
			null
		);
	}

	@Override
	protected boolean isCancelEnabled() {
		return true;
	}
	
}
