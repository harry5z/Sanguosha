package net.client;

import java.util.List;
import java.util.Stack;

import core.PlayerInfo;
import core.client.game.operations.Operation;
import net.Channel;
import ui.game.Activatable;
import ui.game.GamePanelUI;

/**
 * Main Display gui, also monitors card/target selections, confirm/cancel/end
 * selections, etc.
 * 
 * @author Harry
 *
 */
public class GamePanel implements ClientPanel<GamePanelUI> {

	private final GamePanelUI panel;
	private final Stack<Operation> currentOperations;
	private Channel channel;
	
	public GamePanel(PlayerInfo info, List<PlayerInfo> players, Channel channel) {
		this.panel = new GamePanelUI(info, this);
		this.channel = channel;
		for (PlayerInfo player : players) {
			if (!player.equals(info)) {
				panel.addPlayer(player);
			}
		}
		this.currentOperations = new Stack<>();
	}
	
	public synchronized void pushOperation(Operation operation, Activatable source) {
		if (source != null) {
			source.setActivated(true);
		}
		operation.onActivated(this, source);
		currentOperations.push(operation);
	}
	
	public synchronized void popOperation() {
		currentOperations.pop();
	}
	
	public synchronized Operation getCurrentOperation() {
		return currentOperations.peek();
	}
	
	public Channel getChannel() {
		return channel;
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
