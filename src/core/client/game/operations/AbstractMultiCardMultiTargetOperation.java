package core.client.game.operations;

import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

import cards.Card;
import cards.equipments.Equipment.EquipmentType;
import commands.game.server.ingame.InGameServerCommand;
import core.player.PlayerCardZone;
import core.player.PlayerCompleteClient;
import core.player.PlayerSimple;
import ui.game.interfaces.CardUI;
import ui.game.interfaces.EquipmentUI;
import ui.game.interfaces.PlayerUI;

public abstract class AbstractMultiCardMultiTargetOperation extends AbstractOperation {
	
	protected final int maxCards;
	protected int maxTargets;
	/**
	 * A queue of selected targets
	 */
	protected final Queue<PlayerUI> targets;
	/**
	 * A map from selected cards to their respective PlayerCardZone
	 */
	protected final Map<CardUI, PlayerCardZone> cards;

	/**
	 * @param maxCards : 0 <= maxCards
	 * @param maxTargets : 0 <= maxTargets
	 */
	public AbstractMultiCardMultiTargetOperation(int maxCards, int maxTargets) {
		this.maxCards = maxCards;
		this.maxTargets = maxTargets;
		this.targets = new LinkedList<>();
		this.cards = new LinkedHashMap<>();
	}
	
	@Override
	public final void onConfirmed() {
		super.onConfirmed();
		this.panel.sendResponse(getCommandOnConfirm());
	}
	
	@Override
	public final void onPlayerClicked(PlayerUI target) {
		if (this.maxTargets == 0) {
			return;
		}
		if (this.targets.contains(target)) { // unselect a selected target
			target.setActivated(false);
			this.targets.remove(target);
		} else { // select a new target, unselect the oldest target if exceeding maximum
			if (this.targets.size() == this.maxTargets) {
				this.targets.poll().setActivated(false);
			}
			target.setActivated(true);
			this.targets.add(target);
		}
		this.panel.getGameUI().setCancelEnabled(this.isCancelEnabled());
		this.panel.getGameUI().setConfirmEnabled(this.isConfirmEnabled());
	}
	
	@Override
	public final void onCardClicked(CardUI card) {
		if (this.maxCards == 0) {
			this.onCardActivatorClicked(card);
			return;
		}
		this.onCardClicked(card, PlayerCardZone.HAND);
	}
	
	private final void onEquipmentClicked(EquipmentUI equipment) {
		if (this.maxCards == 0) {
			return;
		}
		this.onCardClicked(equipment, PlayerCardZone.EQUIPMENT);
	}
	
	private final void onCardClicked(CardUI card, PlayerCardZone zone) {
		if (this.cards.containsKey(card)) { // unselect a selected card
			this.cards.remove(card);
			card.setActivated(false);
		} else { // select a new card, unselect the oldest card if exceeding maximum
			if (this.cards.size() == this.maxCards) {
				CardUI oldest = this.getFirstCardUI();
				this.cards.remove(oldest);
				oldest.setActivated(false);
			}
			card.setActivated(true);
			this.cards.put(card, zone);
		}
		this.panel.getGameUI().setCancelEnabled(this.isCancelEnabled());
		this.panel.getGameUI().setConfirmEnabled(this.isConfirmEnabled());
	}
	
	/**
	 * <p>{@inheritDoc}</p>
	 * 
	 * NOTE: Does not set the activator Activatable or Activated
	 */
	@Override
	public final void onLoaded() {
		// set message
		this.panel.getGameUI().setMessage(getMessage());
		// enable viable targets
		this.panel.getGameUI().getOtherPlayersUI().forEach(ui -> {
			if (isPlayerActivatable(ui.getPlayer())) {
				ui.setActivatable(true);
			}
		});
		if (isPlayerActivatable(this.panel.getGameUI().getHeroUI().getPlayer())) {
			this.panel.getGameUI().getHeroUI().setActivatable(true);
		}
		// enable viable cards on hand
		this.panel.getGameUI().getCardRackUI().getCardUIs().forEach(ui -> {
			if (isCardActivatable(ui.getCard())) {
				ui.setActivatable(true);
			}
		});
		// enable viable equipments
		this.panel.getGameUI().getEquipmentRackUI().setActivatable(
			EnumSet.allOf(EquipmentType.class).stream().filter(type -> isEquipmentTypeActivatable(type)).collect(Collectors.toSet()),
			e -> this.onEquipmentClicked(e)
		);
		// enable Cancel & Confirm
		if (isCancelEnabled()) {
			this.panel.getGameUI().setCancelEnabled(true);
		}
		if (isConfirmEnabled()) {
			this.panel.getGameUI().setConfirmEnabled(true);
		}
		
		// custom UI (e.g. activator)
		this.onLoadedCustom();
	}

	/**
	 * <p>{@inheritDoc}</p>
	 * 
	 * NOTE: Does not reset the activator Activatable or Activated
	 */
	@Override
	public final void onUnloaded() {
		this.panel.getGameUI().clearMessage();
		
		this.panel.getGameUI().getOtherPlayersUI().forEach(ui -> ui.setActivatable(false));
		this.panel.getGameUI().getHeroUI().setActivatable(false);
		this.targets.forEach(target -> target.setActivated(false));
		
		this.panel.getGameUI().getCardRackUI().getCardUIs().forEach(ui -> ui.setActivatable(false));
		this.cards.forEach((card, type) -> card.setActivated(false));
		
		this.panel.getGameUI().getEquipmentRackUI().setUnactivatable(EnumSet.allOf(EquipmentType.class));
		this.panel.getGameUI().getEquipmentRackUI().setActivated(EnumSet.allOf(EquipmentType.class), false);
		
		this.panel.getGameUI().setCancelEnabled(false);
		this.panel.getGameUI().setConfirmEnabled(false);	
		
		this.onUnloadedCustom();
	}
	
	/**
	 * Get the first card UI selected, or null if no card selected
	 * 
	 * @return the first selected card UI, or null
	 */
	protected CardUI getFirstCardUI() {
		if (this.cards.isEmpty()) {
			return null;
		}
		return this.cards.keySet().iterator().next();
	}
	
	/**
	 * Get the player themselves
	 * 
	 * @return the player
	 */
	protected PlayerCompleteClient getSelf() {
		return this.panel.getGameState().getSelf();
	}
	
	/**
	 * When maxCards is 0 and a card is clicked, it is assumed that the card clicked 
	 * is the activator. By default, this method calls {@link #onCanceled()}
	 * 
	 * @param card : card clicked, presumed to be the activator
	 */
	protected void onCardActivatorClicked(CardUI card) {
		// Only the currently selected card should be clickable by implementation
		// Behave as if the CANCEL button is pressed
		this.onCanceled();
	}
	
	/**
	 * Checks the conditions and return whether the CONFIRM button should be enabled
	 * 
	 * @return whether CONFIRM button should be enabled
	 */
	protected abstract boolean isConfirmEnabled();
	
	/**
	 * Checks the conditions and return whether the CANCEL button should be enabled
	 * 
	 * @return whether CANCEL button should be enabled
	 */
	protected abstract boolean isCancelEnabled();
	
	/**
	 * Checks whether a certain type of equipment can be selected
	 * 
	 * @param type : An {@link EquipmentType}
	 * @return whether this type of equipment can be selected
	 */
	protected abstract boolean isEquipmentTypeActivatable(EquipmentType type);
	
	/**
	 * Checks whether a card on hand can be selected
	 * 
	 * @param card : A Card on hand
	 * @return whether this card can be selected
	 */
	protected abstract boolean isCardActivatable(Card card);
	
	/**
	 * Checks whether a player can be a valid target, including oneself
	 * 
	 * @param player : A player, including oneself
	 * @return whether this player can be a target
	 */
	protected abstract boolean isPlayerActivatable(PlayerSimple player);
	
	/**
	 * Message to be shown to player
	 * 
	 * @return the message to be shown on the player panel
	 */
	protected abstract String getMessage();
	
	/**
	 * <p>Performs additional UI changes on load. For example, if there is an Activator,
	 * it should be set Activated (and most likely also Activatable)</p>
	 * {@link #onLoadedCustom()} is called last. If any UI element needs to be refreshed,
	 * it needs to be refreshed in {@link #onLoadedCustom()}
	 */
	protected abstract void onLoadedCustom();
	
	/**
	 * Performs additional UI reset on unload. For example, if there is an Activator,
	 * it should be set not Activated (and most likely also not Activatable)
	 */
	protected abstract void onUnloadedCustom();
	
	/**
	 * Get the command to be sent when CONFIRM is pressed
	 * 
	 * @return the command to be sent when CONFIRM is pressed
	 */
	protected abstract InGameServerCommand getCommandOnConfirm();

}
