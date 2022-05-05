package core.client.game.operations;

import cards.Card;
import cards.equipments.Equipment.EquipmentType;
import ui.game.interfaces.Activatable;
import ui.game.interfaces.CardUI;

public abstract class AbstractCardInitiatedMultiTargetOperation extends AbstractMultiCardMultiTargetOperation {
	
	protected final CardUI activator;
	
	/**
	 * @param card : the card that activated this Operation
	 * @param maxTargets : max number of targets selectable
	 */
	public AbstractCardInitiatedMultiTargetOperation(Activatable card, int maxTargets) {
		super(0, maxTargets);
		this.activator = (CardUI) card;
	}
	
	@Override
	protected final boolean isCardActivatable(Card card) {
		return false;
	}
	
	@Override
	protected boolean isConfirmEnabled() {
		return this.targets.size() == this.maxTargets;
	}
	
	@Override
	protected final boolean isCancelEnabled() {
		return true;
	}
	
	@Override
	protected final boolean isEquipmentTypeActivatable(EquipmentType type) {
		return false;
	}
	
	@Override
	public void onLoadedCustom() {
		this.activator.setActivatable(true);
		this.activator.setActivated(true);
	}
	
	@Override
	public void onUnloadedCustom() {
		this.activator.setActivatable(false);
		this.activator.setActivated(false);
	}
	
}
