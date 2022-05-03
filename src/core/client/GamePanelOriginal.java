package core.client;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import javax.swing.JPanel;

import core.GameState;
import core.client.game.event.ClientGameEvent;
import core.client.game.listener.ClientEventListener;
import core.client.game.operations.Operation;
import core.player.PlayerInfo;
import net.Channel;
import net.client.ClientMessageListener;
import ui.game.GamePanelGui;
import ui.game.interfaces.GameUI;

/**
 * Main Display gui, also monitors card/target selections, confirm/cancel/end
 * selections, etc.
 * 
 * @author Harry
 *
 */
public class GamePanelOriginal implements GamePanel {

	private final GamePanelGui panel;
	private final Stack<Operation> currentOperations;
	private final Map<Class<? extends ClientGameEvent>, Set<ClientEventListener<? extends ClientGameEvent>>> listeners;
	private final Channel channel;
	
	public GamePanelOriginal(PlayerInfo info, List<PlayerInfo> players, Channel channel) {
		GamePanelGui panel = new GamePanelGui(info, this);
		this.channel = channel;
		for (PlayerInfo player : players) {
			if (!player.equals(info)) {
				panel.addPlayer(player);
			}
		}
		this.listeners = new HashMap<>();
		this.currentOperations = new Stack<>();
		this.panel = panel;
	}
	
	@Override
	public void registerEventListener(ClientEventListener<? extends ClientGameEvent> listener) {
		if (listener == null) {
			return;
		}
		if (this.listeners.containsKey(listener.getEventClass())) {
			this.listeners.get(listener.getEventClass()).add(listener);
		} else {
			this.listeners.put(listener.getEventClass(), new HashSet<>(Set.of(listener)));
		}
	}
	
	@Override
	public void removeEventListener(ClientEventListener<? extends ClientGameEvent> listener) {
		if (listener == null) {
			return;
		}
		if (!this.listeners.containsKey(listener.getEventClass())) {
			return;
		}
		
		this.listeners.get(listener.getEventClass()).removeIf(l -> {
			if (l.equals(listener)) {
				l.onDeactivated(this);
				return true;
			} else {
				return false;
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends ClientGameEvent> void emit(T event) {
		if (!this.listeners.containsKey(event.getClass())) {
			return;
		}
		
		for (ClientEventListener<? extends ClientGameEvent> listener : this.listeners.get(event.getClass())) {
			// By implementation of #registerEventListener, this is correct
			((ClientEventListener<T>) listener).handle(event, this);
		}
	}
	
	@Override
	public synchronized void pushOperation(Operation operation) {
		if (!currentOperations.empty()) {
			currentOperations.peek().onUnloaded();
		}
		operation.onActivated(this);
		currentOperations.push(operation);
	}
	
	@Override
	public synchronized void popOperation() {
		if (!currentOperations.empty()) {
			currentOperations.pop();
		}
	}
	
	@Override
	public synchronized Operation getCurrentOperation() {
		try {
			return currentOperations.peek();
		} catch (EmptyStackException e) {
			return null;
		}
	}
	
	@Override
	public Channel getChannel() {
		return channel;
	}
	
	@Override
	public GameUI getGameUI() {
		return panel;
	}

	@Override
	public ClientMessageListener getMessageListener() {
		// TODO implement a message box
		return null;
	}

	@Override
	public JPanel getPanelUI() {
		return panel;
	}

	@Override
	public GameState getGameState() {
		return panel;
	}

}
