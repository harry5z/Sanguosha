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
import core.player.PlayerSimple;
import ui.game.interfaces.CardUI;
import ui.game.interfaces.EquipmentUI;
import ui.game.interfaces.PlayerUI;

public abstract class AbstractMultiCardMultiTargetOperation extends AbstractOperation {
	
	private final int maxCards;
	private final int maxTargets;
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
	 * @param maxTargest : 0 <= maxTargets
	 */
	public AbstractMultiCardMultiTargetOperation(int maxCards, int maxTargest) {
		this.maxCards = maxCards;
		this.maxTargets = maxTargest;
		this.targets = new LinkedList<>();
		this.cards = new LinkedHashMap<>();
	}
	
	@Override
	public final void onConfirmed() {
		super.onConfirmed();
		this.panel.getChannel().send(getCommandOnConfirm());
	}
	
	@Override
	public final void onPlayerClicked(PlayerUI target) {
		if (this.maxTargets == 0) {
			return;
		}
		if (this.targets.contains(target)) { // unselect a selected target
			target.setActivated(false);
			this.targets.remove(target);
			this.panel.getGameUI().setConfirmEnabled(this.isConfirmEnabled());
		} else { // select a new target, unselect the oldest target if exceeding maximum
			if (this.targets.size() == this.maxTargets) {
				this.targets.poll().setActivated(false);
			}
			target.setActivated(true);
			this.targets.add(target);
			this.panel.getGameUI().setConfirmEnabled(this.isConfirmEnabled());
		}
	}
	
	@Override
	public final void onCardClicked(CardUI card) {
		if (this.maxCards == 0) {
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
			this.panel.getGameUI().setConfirmEnabled(this.isConfirmEnabled());
		} else { // select a new card, unselect the oldest card if exceeding maximum
			if (this.cards.size() == this.maxCards) {
				CardUI oldest = this.cards.keySet().iterator().next();
				this.cards.remove(oldest);
				oldest.setActivated(false);
			}
			card.setActivated(true);
			this.cards.put(card, zone);
			this.panel.getGameUI().setConfirmEnabled(this.isConfirmEnabled());
		}
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
		this.panel.getGameUI().setCancelEnabled(true);
		this.panel.getGameUI().setConfirmEnabled(isConfirmEnabled());
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
	 * Checks the conditions and return whether the CONFIRM button should be enabled
	 * 
	 * @return whether CONFIRM button should be enabled
	 */
	protected abstract boolean isConfirmEnabled();
	
	/**
	 * Checks whether a certain type of equipment should be activatable
	 * 
	 * @param type : An {@link EquipmentType}
	 * @return whether this type of equipment should be activatable
	 */
	protected abstract boolean isEquipmentTypeActivatable(EquipmentType type);
	
	/**
	 * Checks whether a card on hand should be activatable
	 * 
	 * @param card : A Card on hand
	 * @return whether this card should be activatable
	 */
	protected abstract boolean isCardActivatable(Card card);
	
	/**
	 * Checks whether a player should be activatable, including oneself
	 * 
	 * @param player : A player, including oneself
	 * @return whether this player should be activatable
	 */
	protected abstract boolean isPlayerActivatable(PlayerSimple player);
	
	/**
	 * Message to be shown to player
	 * 
	 * @return the message to be shown on the player panel
	 */
	protected abstract String getMessage();
	
	/**
	 * Performs additional UI changes on load. For example, if there is an Activator,
	 * it should be set Activated (and most likely also Activatable)
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
