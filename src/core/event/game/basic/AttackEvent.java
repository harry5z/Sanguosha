package core.event.game.basic;

import cards.basics.Attack;
import core.player.PlayerInfo;
import core.server.game.controllers.AttackGameController;

public class AttackEvent extends AbstractBasicGameEvent {
	
	private final AttackGameController controller;
	private Attack card;

	public AttackEvent(PlayerInfo targetInfo, Attack card, AttackGameController controller) {
		super(targetInfo);
		this.card = card;
		this.controller = controller;
	}
	
	public AttackGameController getController() {
		return this.controller;
	}
	
	public Attack getAttackCard() {
		return this.card;
	}

}
