package core.client.game.operations.basics;

import java.util.stream.Collectors;

import cards.basics.Attack;
import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.InitiateAttackInGameServerCommand;
import core.client.game.event.InitiateAttackClientGameEvent;
import core.client.game.operations.AbstractMultiTargetCardOperation;
import ui.game.interfaces.CardUI;
import ui.game.interfaces.GameUI;
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
		GameUI panelUI = this.panel.getGameUI();
		for (PlayerUI other : panelUI.getOtherPlayersUI()) {
			if (this.panel.getGameState().getSelf().isPlayerInAttackRange(other.getPlayer(), this.panel.getGameState().getNumberOfPlayersAlive())) {
				other.setActivatable(true);
			}
		}
		panelUI.setCancelEnabled(true);
		this.panel.emit(new InitiateAttackClientGameEvent(true, this));
	}

}
