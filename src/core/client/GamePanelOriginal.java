package core.client;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import core.client.game.event.ClientGameEvent;
import core.client.game.listener.ClientEventListener;
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
	private final Map<Class<? extends ClientGameEvent>, Set<ClientEventListener<?, HeroOriginal>>> listeners;
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
	public void registerEventListener(ClientEventListener<? extends ClientGameEvent, HeroOriginal> listener) {
		if (this.listeners.containsKey(listener.getEventClass())) {
			this.listeners.get(listener.getEventClass()).add(listener);
		} else {
			this.listeners.put(listener.getEventClass(), Set.of(listener));
		}
	}
	
	@Override
	public void removeEventListener(ClientEventListener<? extends ClientGameEvent, HeroOriginal> listener) {
		if (!this.listeners.containsKey(listener.getEventClass())) {
			return;
		}
		
		this.listeners.get(listener.getEventClass()).remove(listener);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends ClientGameEvent> void emit(E event) {
		if (!this.listeners.containsKey(event.getClass())) {
			return;
		}
		
		for (ClientEventListener<?, HeroOriginal> listener : this.listeners.get(event.getClass())) {
			((ClientEventListener<E, HeroOriginal>) listener).handle(event, this);
		}
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
	public ClientGameUI<HeroOriginal> getContent() {
		return panel;
	}

	@Override
	public ClientMessageListener getMessageListener() {
		return null; // message box
	}

}
