package core.client.game.operations.skills;

import cards.Card;
import cards.Card.Color;
import cards.equipments.Equipment.EquipmentType;
import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.SuddenStrikeSkillInGameServerCommand;
import core.player.PlayerSimple;
import ui.game.interfaces.SkillUI;

public class SuddenStrikeSkillOperation extends AbstractMultiCardMultiTargetSkillOperation {

	public SuddenStrikeSkillOperation(SkillUI skill) {
		super(skill, 1, 1);
	}

	@Override
	protected boolean isConfirmEnabled() {
		return this.cards.size() == 1 && this.targets.size() == 1;
	}

	@Override
	protected boolean isEquipmentTypeActivatable(EquipmentType type) {
		return (
			this.panel.getGameState().getSelf().isEquipped(type) && 
			this.panel.getGameState().getSelf().getEquipment(type).getColor() == Color.BLACK
		);
	}

	@Override
	protected boolean isCardActivatable(Card card) {
		return card.getColor() == Color.BLACK;
	}

	@Override
	protected boolean isPlayerActivatable(PlayerSimple player) {
		return !this.panel.getGameState().getSelf().equals(player);
	}

	@Override
	protected String getMessage() {
		return "Select a BLACK card to use as Sabotage on a target";
	}

	@Override
	protected InGameServerCommand getCommandOnConfirm() {
		return new SuddenStrikeSkillInGameServerCommand(
			this.getFirstCardUI().getCard(),
			this.cards.entrySet().iterator().next().getValue(),
			this.targets.peek().getPlayer().getPlayerInfo()
		);
	}

}
