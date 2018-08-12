package core.client.game.operations.basics;

import cards.basics.Attack;
import commands.game.server.GameServerCommand;
import commands.game.server.InitiateAttackGameServerCommand;
import core.client.game.operations.AbstractSingleTargetCardOperation;
import core.heroes.Hero;
import core.player.PlayerComplete;
import ui.game.interfaces.CardUI;
import ui.game.interfaces.ClientGameUI;
import ui.game.interfaces.PlayerUI;

public class InitiateAttackOperation extends AbstractSingleTargetCardOperation {

	@Override
	protected GameServerCommand getCommand() {
		return new InitiateAttackGameServerCommand(
			this.source,
			this.targetUI.getPlayer().getPlayerInfo(),
			(Attack) ((CardUI) this.activator).getCard()
		);
	}
	
	@Override
	protected void setupTargetSelection() {
		ClientGameUI<? extends Hero> panelUI = this.panel.getContent();
		PlayerComplete self = panelUI.getSelf();
		for (PlayerUI other : panelUI.getOtherPlayersUI()) {
			if (self.isPlayerInRange(other.getPlayer(), panelUI.getNumberOfPlayers())) {
				other.setActivatable(true);
			}
		}
		panelUI.setCancelEnabled(true);
	}

}
