package commands.game.client;

import java.util.HashSet;
import java.util.Set;

import cards.Card;
import cards.equipments.Equipment.EquipmentType;
import cards.equipments.weapons.SerpentSpear;
import commands.game.server.ingame.EndStageInGameServerCommand;
import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.SerpentSpearInitiateAttackInGameServerCommand;
import core.client.game.operations.Operation;
import core.client.game.operations.mechanics.DealOperation;
import core.heroes.skills.ActiveSkill;
import core.heroes.skills.Skill;
import core.player.PlayerCompleteServer;

public class DealStartGameUIClientCommmand extends AbstractSingleTargetOperationGameClientCommand {

	private static final long serialVersionUID = 1L;
	
	private final transient PlayerCompleteServer player;
	
	public DealStartGameUIClientCommmand(PlayerCompleteServer target) {
		super(target.getPlayerInfo());
		this.player = target;
	}

	@Override
	protected boolean shouldClearGamePanel() {
		return true;
	}

	@Override
	protected Operation getOperation() {
		return new DealOperation();
	}

	@Override
	public Set<Class<? extends InGameServerCommand>> getAllowedResponseTypes() {
		Set<Class<? extends InGameServerCommand>> types = new HashSet<>();
		// allow ending the turn
		types.add(EndStageInGameServerCommand.class);
		// allow using available cards
		for (Card card : player.getCardsOnHand()) {
			Class<? extends InGameServerCommand> type = card.getAllowedDealPhaseResponseType();
			if (type != null) {
				types.add(type);
			}
		}
		// WARNING: directly added Serpent Spear because it's the only equipment with a Deal Phase 
		// ability. This would not be scalable if other equipments with the same ability
		// are added
		if (
			player.isEquipped(EquipmentType.WEAPON) &&
			player.getEquipment(EquipmentType.WEAPON) instanceof SerpentSpear &&
			player.getHandCount() >= 2
		) {
			types.add(SerpentSpearInitiateAttackInGameServerCommand.class);
		}
		// allow use of skill that can be used in Deal phase
		for (Skill skill : player.getHero().getSkills()) {
			if (skill instanceof ActiveSkill) {
				Class<? extends InGameServerCommand> type = ((ActiveSkill) skill).getAllowedResponseType(this);
				if (type != null) {
					types.add(type);
				}
			}
		}
		return types;
	}

	@Override
	public InGameServerCommand getDefaultResponse() {
		return new EndStageInGameServerCommand();
	}

}
