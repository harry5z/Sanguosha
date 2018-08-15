package commands.game.client;

import java.util.Collection;

import core.client.GamePanel;
import core.client.game.operations.basics.UseCardReactionOperation;
import core.event.game.basic.RequestUseCardEvent.RequestUseCardPredicate;
import core.heroes.Hero;
import core.player.PlayerInfo;

public class RequestUseCardGameUIClientCommand extends GeneralGameUIClientCommand {

	private static final long serialVersionUID = 1L;
	
	private final PlayerInfo target;
	private final Collection<RequestUseCardPredicate> predicates;
	
	public RequestUseCardGameUIClientCommand(PlayerInfo target, Collection<RequestUseCardPredicate> predicates) {
		this.target = target;
		this.predicates = predicates;
	}

	@Override
	protected void execute(GamePanel<? extends Hero> panel) {
		if (panel.getContent().getSelf().getPlayerInfo().equals(this.target)) {
			panel.pushOperation(new UseCardReactionOperation(this.predicates));
		} else {
			panel.getContent().getOtherPlayerUI(this.target).showCountdownBar();
		}
	}

}
