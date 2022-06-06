package core.client.game.operations.skills;

import java.util.Map;
import java.util.stream.Collectors;

import cards.Card;
import cards.equipments.Equipment.EquipmentType;
import commands.server.ingame.InGameServerCommand;
import commands.server.ingame.ReconsiderationSkillInGameServerCommand;
import core.player.PlayerCardZone;
import core.player.PlayerSimple;
import ui.game.interfaces.SkillUI;

public class ReconsiderationSkillOperation extends AbstractMultiCardMultiTargetSkillOperation {

	public ReconsiderationSkillOperation(SkillUI skill) {
		super(skill, Integer.MAX_VALUE, 0);
	}

	@Override
	protected boolean isConfirmEnabled() {
		return this.cards.size() > 0;
	}

	@Override
	protected boolean isEquipmentTypeActivatable(EquipmentType type) {
		return true;
	}

	@Override
	protected boolean isCardActivatable(Card card) {
		return true;
	}

	@Override
	protected boolean isPlayerActivatable(PlayerSimple player) {
		return false;
	}

	@Override
	protected String getMessage() {
		return "Select cards to discard";
	}

	@Override
	protected InGameServerCommand getCommandOnConfirm() {
		Map<Card, PlayerCardZone> map = this.cards.entrySet().stream().collect(Collectors.toMap(
			entry -> entry.getKey().getCard(),
			entry -> entry.getValue()
		));
		return new ReconsiderationSkillInGameServerCommand(map);
	}

}
