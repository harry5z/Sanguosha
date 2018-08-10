package core.server.game.controllers;

import cards.equipments.Equipment;
import core.event.game.EquipEvent;
import core.event.game.UnequipEvent;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.InvalidPlayerCommandException;
import utils.EnumWithNextStage;

public class EquipGameController extends AbstractGameController {

	public static enum EquipStage implements EnumWithNextStage<EquipStage> {
		UNEQUIP,
		EQUIP,
		END;
	}
	
	private EquipStage stage;
	private final PlayerCompleteServer player;
	private final Equipment equipment;
	
	public EquipGameController(Game game, PlayerCompleteServer player, Equipment equipment) {
		super(game);
		this.player = player;
		this.equipment = equipment;
		this.stage = EquipStage.UNEQUIP;
	}

	@Override
	public void proceed() {
		switch (this.stage) {
			case UNEQUIP:
				try {
					this.player.removeCardFromHand(this.equipment);
				} catch (InvalidPlayerCommandException e) {
					
				}
				this.stage = this.stage.nextStage(); // proceed to next stage regardless
				try {
					this.game.emit(new UnequipEvent(this.player, this.equipment.getEquipmentType()));
				} catch (GameFlowInterruptedException e) {
					return;
				}
				this.proceed();
				break;
			case EQUIP:
				this.stage = this.stage.nextStage(); // proceed to next stage regardless
				try {
					this.game.emit(new EquipEvent(this.player, this.equipment));
				} catch (GameFlowInterruptedException e) {
					return;
				}
				this.proceed();
				break;
			case END:
				this.onCompleted();
				break;
		}
	}

}
