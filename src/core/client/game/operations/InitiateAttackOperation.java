package core.client.game.operations;

import core.PlayerInfo;
import net.client.GamePanel;
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
		}
		activator.setActivated(false);
		panel.popOperation();
	}
	
	@Override
	public void onConfirmed() {
		panel.getChannel().send(null); // TODO
	}

	@Override
	public void onActivated(GamePanel panel, Activatable activator) {
		this.activator = activator;
		this.panel = panel;
		GamePanelUI panelUI = panel.getContent();
		this.source = panelUI.getSelf().getPlayerInfo();
		panelUI.setCancelEnabled(true);
	}

}
