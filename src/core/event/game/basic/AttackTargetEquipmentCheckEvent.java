package core.event.game.basic;

import cards.basics.Attack;
import core.player.PlayerInfo;
import core.server.game.controllers.mechanics.AttackResolutionGameController;

public class AttackTargetEquipmentCheckEvent extends AbstractSingleTargetGameEvent {
	
	private final AttackResolutionGameController controller;
	private Attack card;

	public AttackTargetEquipmentCheckEvent(PlayerInfo targetInfo, Attack card, AttackResolutionGameController controller) {
		super(targetInfo);
		this.card = card;
		this.controller = controller;
	}
	
	public AttackResolutionGameController getController() {
		return this.controller;
	}
	
	public Attack getAttackCard() {
		return this.card;
	}

}
