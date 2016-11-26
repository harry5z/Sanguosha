package core.client.game.operations.basics;

import cards.basics.Attack;
import commands.game.server.InitiateAttackGameServerCommand;
import core.client.GamePanel;
import core.client.game.operations.Operation;
import core.heroes.Hero;
import core.player.PlayerComplete;
import core.player.PlayerInfo;
import ui.game.interfaces.Activatable;
import ui.game.interfaces.CardUI;
import ui.game.interfaces.ClientGameUI;
import ui.game.interfaces.PlayerUI;

public class InitiateAttackOperation implements Operation {

	private GamePanel<? extends Hero> panel;
	private Activatable activator;
	private PlayerInfo source;
	private PlayerUI targetUI;
	
	@Override
	public void onPlayerClicked(PlayerUI player) {
		PlayerInfo info = player.getPlayer().getPlayerInfo();
		if (targetUI == null) {
			targetUI = player;
			targetUI.setActivated(true);
			panel.getContent().setConfirmEnabled(true);
		} else if (info.equals(targetUI.getPlayer().getPlayerInfo())) {
			targetUI.setActivated(false);
			targetUI = null;
			panel.getContent().setConfirmEnabled(false);
		} else {
			targetUI.setActivated(false);
			targetUI = player;
			targetUI.setActivated(true);
		}
	}
	
	@Override
	public void onCardClicked(CardUI card) {
		onCanceled();
		if (card != activator) {
			panel.getCurrentOperation().onCardClicked(card);
		}
	}
	
	@Override
	public void onEnded() {
		onCanceled();
		panel.getCurrentOperation().onEnded();
	}
	
	@Override
	public void onCanceled() {
		if (targetUI != null) {
			targetUI.setActivated(false);
			panel.getContent().setConfirmEnabled(false);
		}
		for (PlayerUI other : panel.getContent().getOtherPlayersUI()) {
			other.setActivatable(false);
		}
		panel.getContent().setCancelEnabled(false);
		activator.setActivated(false);
		panel.popOperation();
	}
	
	@Override
	public void onConfirmed() {
		onCanceled();
		panel.getCurrentOperation().onConfirmed();
		panel.getChannel().send(new InitiateAttackGameServerCommand(source, targetUI.getPlayer().getPlayerInfo(), (Attack) ((CardUI) activator).getCard()));
	}

	@Override
	public void onActivated(GamePanel<? extends Hero> panel, Activatable activator) {
		this.activator = activator;
		this.panel = panel;
		ClientGameUI<? extends Hero> panelUI = panel.getContent();
		PlayerComplete self = panelUI.getSelf();
		this.source = self.getPlayerInfo();
		for (PlayerUI other : panelUI.getOtherPlayersUI()) {
			if (self.isPlayerInRange(other.getPlayer(), panelUI.getNumberOfPlayers())) {
				other.setActivatable(true);
			}
		}
		panelUI.setCancelEnabled(true);
	}

}
