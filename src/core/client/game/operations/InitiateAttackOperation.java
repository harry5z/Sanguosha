package core.client.game.operations;

import commands.game.server.InitiateAttackGameServerCommand;
import core.PlayerInfo;
import net.client.GamePanel;
import player.PlayerComplete;
import ui.game.Activatable;
import ui.game.CardGui;
import ui.game.GamePanelUI;
import ui.game.PlayerGui;

public class InitiateAttackOperation implements Operation {

	private GamePanel panel;
	private Activatable activator;
	private PlayerInfo source;
	private PlayerGui targetUI;
	
	@Override
	public void onPlayerClicked(PlayerGui player) {
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
	public void onCardClicked(CardGui card) {
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
		for (PlayerGui other : panel.getContent().getOtherPlayers()) {
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
		panel.getChannel().send(new InitiateAttackGameServerCommand(source, targetUI.getPlayer().getPlayerInfo(), ((CardGui) activator).getCard()));
	}

	@Override
	public void onActivated(GamePanel panel, Activatable activator) {
		this.activator = activator;
		this.panel = panel;
		GamePanelUI panelUI = panel.getContent();
		PlayerComplete self = panelUI.getSelf();
		this.source = self.getPlayerInfo();
		for (PlayerGui other : panelUI.getOtherPlayers()) {
			if (self.isPlayerInRange(other.getPlayer(), panelUI.getNumberOfPlayers())) {
				other.setActivatable(true);
			}
		}
		panelUI.setCancelEnabled(true);
	}

}
