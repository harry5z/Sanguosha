package core.client.game.operations.skills;

import java.util.stream.Collectors;

import cards.Card;
import cards.equipments.Equipment.EquipmentType;
import commands.game.server.ingame.ArrowSalvoSkillIngameServerCommand;
import commands.game.server.ingame.InGameServerCommand;
import core.player.PlayerSimple;
import ui.game.interfaces.SkillUI;

public class ArrowSalvoSkillOperation extends AbstractMultiCardMultiTargetSkillOperation {

	public ArrowSalvoSkillOperation(SkillUI skill) {
		super(skill, 2, 0);
	}

	@Override
	protected boolean isConfirmEnabled() {
		return this.cards.size() == 2;
	}

	@Override
	protected boolean isEquipmentTypeActivatable(EquipmentType type) {
		return false;
	}

	@Override
	protected boolean isCardActivatable(Card card) {
		if (this.cards.isEmpty()) {
			return true;
		}
		return card.getSuit() == this.getFirstCardUI().getCard().getSuit();
	}

	@Override
	protected boolean isPlayerActivatable(PlayerSimple player) {
		return false;
	}

	@Override
	protected String getMessage() {
		return "Select 2 cards of the same suit to use as Arrow Salvo";
	}

	@Override
	protected InGameServerCommand getCommandOnConfirm() {
		return new ArrowSalvoSkillIngameServerCommand(cards.keySet().stream().map(ui -> ui.getCard()).collect(Collectors.toList()));
	}

}
