package commands.game.client;

import java.util.HashSet;
import java.util.Set;

import cards.equipments.Equipment.EquipmentType;
import cards.equipments.weapons.SerpentSpear;
import commands.game.server.ingame.AttackReactionInGameServerCommand;
import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.SerpentSpearAttackReactionInGameServerCommand;
import core.client.game.operations.Operation;
import core.client.game.operations.basics.AttackReactionOperation;
import core.heroes.skills.ActiveSkill;
import core.heroes.skills.Skill;
import core.player.PlayerCompleteServer;

public class RequestAttackGameUIClientCommand extends AbstractSingleTargetOperationGameClientCommand {

	private static final long serialVersionUID = 1L;
	
	private final transient PlayerCompleteServer player;
	private final String message;
	
	public RequestAttackGameUIClientCommand(PlayerCompleteServer target, String message) {
		super(target.getPlayerInfo());
		this.player = target;
		this.message = message;
	}

	@Override
	protected Operation getOperation() {
		return new AttackReactionOperation(this.message);
	}

	@Override
	public Set<Class<? extends InGameServerCommand>> getAllowedResponseTypes() {
		Set<Class<? extends InGameServerCommand>> types = new HashSet<>();
		// allow regular Attack reaction (with an Attack card)
		types.add(AttackReactionInGameServerCommand.class);
		// WARNING: directly added Serpent Spear because it's the only equipment with Attack
		// reaction ability. This would not be scalable if other equipments with the same ability
		// are added
		if (
			player.isEquipped(EquipmentType.WEAPON) && 
			player.getEquipment(EquipmentType.WEAPON) instanceof SerpentSpear &&
			player.getHandCount() >= 2
		) {
			types.add(SerpentSpearAttackReactionInGameServerCommand.class);
		}
		// allow skills that can turn something into Attack
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

}
