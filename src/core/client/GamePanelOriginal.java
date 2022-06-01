package core.client;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import javax.swing.JPanel;

import commands.game.server.ingame.InGameServerCommand;
import core.GameState;
import core.client.game.event.ClientGameEvent;
import core.client.game.listener.ClientEventListener;
import core.client.game.operations.Operation;
import core.player.PlayerInfo;
import net.Channel;
import net.client.ClientMessageListener;
import ui.game.BattleLogGui;
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

	private final JPanel displayPanel;
	private final GamePanelGui mainPanel;
	private final BattleLogGui battleLogPanel;
	private final Stack<Operation> currentOperations;
	private final Map<Class<? extends ClientGameEvent>, Set<ClientEventListener<? extends ClientGameEvent>>> listeners;
	private final Channel channel;
	private UUID uuid;
	private TimerTask actionTimeoutTask;
	private final Timer timer;
	
	public GamePanelOriginal(PlayerInfo info, List<PlayerInfo> players, Channel channel) {
		GamePanelGui panel = new GamePanelGui(info, this);
		this.channel = channel;
		for (PlayerInfo player : players) {
			if (!player.equals(info)) {
				panel.addPlayer(player);
			}
		}
		panel.validate();
		panel.repaint();
		this.listeners = new HashMap<>();
		this.currentOperations = new Stack<>();
		this.mainPanel = panel;
		this.battleLogPanel = new BattleLogGui();
		this.displayPanel = new JPanel();
		this.displayPanel.add(panel);
		this.displayPanel.add(battleLogPanel);
		this.uuid = null;
		this.actionTimeoutTask = null;
		this.timer = new Timer();
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
				listener.handleOnUnloaded(this);
				return true;
			}
			return false;
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
			if (event.isLoadedStage()) {
				((ClientEventListener<T>) listener).handleOnLoaded(event, this);
			} else {
				((ClientEventListener<T>) listener).handleOnUnloaded(this);
			}
		}
	}
	
	@Override
	public synchronized void pushPlayerActionOperation(Operation operation, int timeoutMS) {
		if (!currentOperations.empty()) {
			currentOperations.peek().onUnloaded();
			currentOperations.peek().onDeactivated();
		}

		if (this.actionTimeoutTask != null) {
			this.actionTimeoutTask.cancel();
		}
		this.actionTimeoutTask = new TimerTask() {
			@Override
			public void run() {
				synchronized (GamePanelOriginal.this) {
					if (actionTimeoutTask != this) {
						return;
					}
					actionTimeoutTask = null;
					if (!currentOperations.empty()) {
						currentOperations.peek().onUnloaded();
						currentOperations.peek().onDeactivated();
						mainPanel.stopCountdown();
					}
				}
			}
		};
		
		timer.schedule(actionTimeoutTask, timeoutMS);
		mainPanel.showCountdownBar(timeoutMS);
		
		operation.onActivated(this);
		currentOperations.push(operation);
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
	public void sendResponse(InGameServerCommand command) {
		synchronized (this) {
			if (actionTimeoutTask != null) {
				actionTimeoutTask.cancel();
				actionTimeoutTask = null;
			}
		}
		mainPanel.stopCountdown();
		// response ID must be present for the response to be accepted by server
		command.setResponseID(uuid);
		channel.send(command);
	}
	
	@Override
	public GameUI getGameUI() {
		return mainPanel;
	}

	@Override
	public ClientMessageListener getMessageListener() {
		// TODO implement a message box
		return null;
	}

	@Override
	public JPanel getPanelUI() {
		return displayPanel;
	}

	@Override
	public GameState getGameState() {
		return mainPanel;
	}

	@Override
	public void setNextResponseID(UUID id) {
		this.uuid = id;
	}

	public void pushBattleLog(String log) {
		this.battleLogPanel.pushBattleLog(log);
	}

}
