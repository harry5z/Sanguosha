package core.client.game.operations.basics;

import cards.Card;
import cards.basics.Attack;
import commands.game.server.ingame.AttackReactionInGameServerCommand;
import commands.game.server.ingame.InGameServerCommand;
import core.client.GamePanel;
import core.client.game.event.AttackReactionClientGameEvent;
import core.client.game.operations.AbstractCardReactionOperation;
import core.heroes.Hero;
import ui.game.interfaces.Activatable;

public class AttackReactionOperation extends AbstractCardReactionOperation {

	public AttackReactionOperation(String message) {
		super(message);
	}

	@Override
	protected boolean isCardActivatable(Card card) {
		return card instanceof Attack;
	}
	
	@Override
	protected boolean isCancelEnabled() {
		return true;
	}
	
	@Override
	public void onActivated(GamePanel<? extends Hero> panel, Activatable source) {
		super.onActivated(panel, source);
		panel.emit(new AttackReactionClientGameEvent(true));
	}
	
	@Override
	public void onDeactivated() {
		super.onDeactivated();
		this.panel.emit(new AttackReactionClientGameEvent(false));
	}

	@Override
	protected InGameServerCommand getCommand(Card card) {
		return new AttackReactionInGameServerCommand(this.panel.getContent().getSelf().getPlayerInfo(), card);
	}
	
}
