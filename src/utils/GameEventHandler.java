package utils;

import java.util.HashMap;
import java.util.Map;

import core.event.game.GameEvent;
import core.event.handlers.EventHandler;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import exceptions.server.game.GameFlowInterruptedException;

public class GameEventHandler {
	
	private static final String TAG = "GameEventHandler";
	/*
	 * A map from event types to a map from players to event handlers of that type.
	 */
	private Map<Class<? extends GameEvent>, Map<PlayerCompleteServer, EventHandlerStack>> gameEventHandlers = new HashMap<>();
	
	public static class EventHandlerStack {
		
		private class Node {
			Node next;
			EventHandler<? extends GameEvent> handler;
			
			public Node(EventHandler<? extends GameEvent> handler) {
				this.handler = handler;
				this.next = null;
			}
		}
		
		Node root;
		
		public EventHandlerStack(EventHandler<? extends GameEvent> handler) {
			this.root = new Node(handler);
		}
		
		void push(EventHandler<? extends GameEvent> handler) {
			Node node = new Node(handler);
			node.next = this.root;
			this.root = node;
		}
		
		void remove(EventHandler<? extends GameEvent> handler) {
			if (this.root == null) {
				Log.log(TAG, "no matching handler found: no handler for the event");
				return;
			}
			
			if (this.root.handler.equals(handler)) {
				this.root = this.root.next;
				return;
			}
			
			for (Node current = this.root, next = current.next; next != null; current = next, next = next.next) {
				if (next.handler.equals(handler)) {
					current.next = next.next;
					return;
				}
			}
			Log.log(TAG, "no matching handler found");
		}
		
		@SuppressWarnings("unchecked")
		public <T extends GameEvent> void handle(T event, Game game) throws GameFlowInterruptedException {
			for (Node current = this.root; current != null; current = current.next) {
				((EventHandler<T>) current.handler).handle(event, game);
			}
		}
	}
	
	public <T extends GameEvent> void registerEventHandler(EventHandler<T> handler) {
		Class<T> c = handler.getEventClass();
		Map<PlayerCompleteServer, EventHandlerStack> handlerStackMap = this.gameEventHandlers.get(c);
		if (handlerStackMap != null) {
			if (handlerStackMap.containsKey(handler.getPlayerSource())) {
				handlerStackMap.get(handler.getPlayerSource()).push(handler);
			} else {
				handlerStackMap.put(handler.getPlayerSource(), new EventHandlerStack(handler));
			}
		} else {
			Map<PlayerCompleteServer, EventHandlerStack> handlerMap = new HashMap<>();
			handlerMap.put(handler.getPlayerSource(), new EventHandlerStack(handler));
			this.gameEventHandlers.put(c, handlerMap);
		}
	}
	
	public <T extends GameEvent> void removeEventHandler(EventHandler<T> handler) {
		Class<T> c = handler.getEventClass();
		Map<PlayerCompleteServer, EventHandlerStack> map = this.gameEventHandlers.get(c);
		if (map == null) {
			throw new RuntimeException("handler not found, never registered in map");
		}
		EventHandlerStack stack = map.get(handler.getPlayerSource());
		if (stack == null) {
			throw new RuntimeException("handler not found, never registered for player");
		}
		stack.remove(handler);
	}
	
	public <T extends GameEvent> Map<PlayerCompleteServer, EventHandlerStack> getHandlersForEvent(T event) {
		Class<?> eventType = event.getClass();
		if (this.gameEventHandlers.containsKey(eventType)) {
			return this.gameEventHandlers.get(eventType);
		}
		
		while (eventType != null) {
			// also check handlers under a generic event category
			for (Class<?> eventCategory : eventType.getInterfaces()) {
				if (this.gameEventHandlers.containsKey(eventCategory)) {
					return this.gameEventHandlers.get(eventCategory);
				}
			}
			eventType = eventType.getSuperclass();
		}
		
		return null;
	}
}
