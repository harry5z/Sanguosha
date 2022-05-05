package core.client.game.operations.skills;

import cards.Card;
import cards.Card.Color;
import cards.equipments.Equipment.EquipmentType;
import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.InitiateFireAttackInGameServerCommand;
import core.player.PlayerSimple;
import ui.game.interfaces.SkillUI;

public class FireAttackSkillOperation extends AbstractMultiCardMultiTargetSkillOperation {
	
	
	public FireAttackSkillOperation(SkillUI skill) {
		super(skill, 1, 1);
	}
	
	@Override
	protected boolean isConfirmEnabled() {
		return this.cards.size() == 1 && this.targets.size() == 1;
	}

	@Override
	protected boolean isEquipmentTypeActivatable(EquipmentType type) {
		return false;
	}

	@Override
	protected boolean isCardActivatable(Card card) {
		return card.getColor() == Color.RED;
	}

	@Override
	protected boolean isPlayerActivatable(PlayerSimple player) {
		if (player == this.panel.getGameUI().getHeroUI()) {
			// can use Fire Attack on oneself if more than one card on hand
			return player.getHandCount() > 1;
		} else {
			return player.getHandCount() > 0;
		}
	}

	@Override
	protected String getMessage() {
		return "Select a RED card and a target to initiate Fire Attack";
	}

	@Override
	protected InGameServerCommand getCommandOnConfirm() {
		return new InitiateFireAttackInGameServerCommand(
			this.targets.peek().getPlayer().getPlayerInfo(),
			this.cards.keySet().iterator().next().getCard()
		);
	}

}
