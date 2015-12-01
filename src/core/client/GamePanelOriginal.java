package core.client;

import java.util.List;
import java.util.Stack;

import core.client.game.operations.Operation;
import core.heroes.original.HeroOriginal;
import core.player.PlayerInfo;
import net.Channel;
import net.client.ClientMessageListener;
import ui.game.GamePanelGui;
import ui.game.interfaces.Activatable;
import ui.game.interfaces.ClientGameUI;

/**
 * Main Display gui, also monitors card/target selections, confirm/cancel/end
 * selections, etc.
 * 
 * @author Harry
 *
 */
public class GamePanelOriginal implements GamePanel<HeroOriginal> {

	private final ClientGameUI<HeroOriginal> panel;
	private final Stack<Operation> currentOperations;
	private final Channel channel;
	
	public GamePanelOriginal(PlayerInfo info, List<PlayerInfo> players, Channel channel) {
		GamePanelGui panel = new GamePanelGui(info, this);
		this.channel = channel;
		for (PlayerInfo player : players) {
			if (!player.equals(info)) {
				panel.addPlayer(player);
			}
		}
		this.currentOperations = new Stack<>();
		this.panel = panel;
	}
	
	@Override
	public synchronized void pushOperation(Operation operation, Activatable source) {
		if (source != null) {
			source.setActivated(true);
		}
		operation.onActivated(this, source);
		currentOperations.push(operation);
	}
	
	@Override
	public synchronized void popOperation() {
		currentOperations.pop();
	}
	
	@Override
	public synchronized Operation getCurrentOperation() {
		return currentOperations.peek();
	}
	
	@Override
	public Channel getChannel() {
		return channel;
	}
	
	@Override
	public ClientGameUI<HeroOriginal> getContent() {
		return panel;
	}

	@Override
	public ClientMessageListener getMessageListener() {
		return null; // message box
	}

}
