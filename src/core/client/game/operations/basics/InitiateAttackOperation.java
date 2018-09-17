package core.client.game.operations.basics;

import java.util.stream.Collectors;

import cards.basics.Attack;
import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.InitiateAttackInGameServerCommand;
import core.client.game.operations.AbstractMultiTargetCardOperation;
import core.heroes.Hero;
import core.player.PlayerComplete;
import ui.game.interfaces.CardUI;
import ui.game.interfaces.ClientGameUI;
import ui.game.interfaces.PlayerUI;

public class InitiateAttackOperation extends AbstractMultiTargetCardOperation {

	public InitiateAttackOperation() {
		super(1, 1);
	}

	@Override
	protected InGameServerCommand getCommand() {
		return new InitiateAttackInGameServerCommand(
			this.source,
			this.targets.stream().map(target -> target.getPlayer().getPlayerInfo()).collect(Collectors.toSet()),
			(Attack) ((CardUI) this.activator).getCard()
		);
	}
	
	@Override
	protected void setupTargetSelection() {
		ClientGameUI<? extends Hero> panelUI = this.panel.getContent();
		PlayerComplete self = panelUI.getSelf();
		for (PlayerUI other : panelUI.getOtherPlayersUI()) {
			if (self.isPlayerInAttackRange(other.getPlayer(), panelUI.getNumberOfPlayersAlive())) {
				other.setActivatable(true);
			}
		}
		panelUI.setCancelEnabled(true);
	}

}
