package core.client.game.operations.skills;

import cards.Card;
import cards.Card.Color;
import cards.equipments.Equipment.EquipmentType;
import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.ZhugeliangNullificationReactionInGameServerCommand;
import core.player.PlayerSimple;
import ui.game.interfaces.SkillUI;

public class SeeThroughSkillOperation extends AbstractMultiCardMultiTargetSkillOperation {
	
	public SeeThroughSkillOperation(SkillUI skill) {
		super(skill, 1, 0);
	}

	@Override
	protected boolean isConfirmEnabled() {
		return this.cards.size() == 1;
	}

	@Override
	protected boolean isEquipmentTypeActivatable(EquipmentType type) {
		return false;
	}

	@Override
	protected boolean isCardActivatable(Card card) {
		return card.getColor() == Color.BLACK;
	}

	@Override
	protected boolean isPlayerActivatable(PlayerSimple player) {
		return false;
	}

	@Override
	protected String getMessage() {
		return "Select a BLACK card on hand as Nullification";
	}

	@Override
	protected InGameServerCommand getCommandOnConfirm() {
		return new ZhugeliangNullificationReactionInGameServerCommand(this.getFirstCardUI().getCard());
	}
}
