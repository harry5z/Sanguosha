package net.client;

import java.util.List;

import net.Channel;
import ui.game.CardGui;
import ui.game.GamePanelUI;

import commands.game.server.GameServerCommand;
import commands.operations.Operation;

import core.PlayerInfo;

/**
 * Main Display gui, also monitors card/target selections, confirm/cancel/end
 * selections, etc.
 * 
 * @author Harry
 *
 */
public class GamePanel implements ClientPanel<GamePanelUI> {

	private final GamePanelUI panel;
	private Operation currentOperation;
	private final Operation defaultOperation;
	
	public GamePanel(PlayerInfo info, List<PlayerInfo> players, Channel channel) {
		this.panel = new GamePanelUI(info, this);
		for (PlayerInfo player : players) {
			if (!player.equals(info)) {
				panel.addPlayer(player);
			}
		}
		this.currentOperation = this.defaultOperation = new Operation() {

			@Override
			public GameServerCommand generateCommand() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void onCardClicked(CardGui card){
				GamePanel.this.currentOperation = card.getCard().generateOperation(GamePanel.this);
			}
			
		};
	}
	
	public Operation getCurrentOperation() {
		return currentOperation;
	}
	
	public GamePanelUI getPanel() {
		return panel;
	}
	
	@Override
	public GamePanelUI getContent() {
		return panel;
	}

	@Override
	public ClientMessageListener getMessageListener() {
		return null; // message box
	}

}
