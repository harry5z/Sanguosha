package core.server.game.controllers.mechanics;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import cards.basics.Attack;
import core.event.game.basic.AttackPreAcquisitionWeaponAbilitiesCheckEvent;
import core.player.PlayerCompleteServer;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractGameController;
import core.server.game.controllers.GameControllerStage;
import exceptions.server.game.GameFlowInterruptedException;

public class AttackGameController extends AbstractGameController<AttackGameController.AttackStage> {

	public static enum AttackStage implements GameControllerStage<AttackStage> {
		PRE_TARGET_ACQUISITION_WEAPON_ABILITIES,
		TARGET_ACQUISITION,
		ATTACK_RESOLUTION,
		END,
	}
	
	private final PlayerCompleteServer source;
	private final Set<PlayerCompleteServer> targets;
	private final int damageAmount;
	private Attack attack;
	
	public AttackGameController(PlayerCompleteServer source, Collection<PlayerCompleteServer> targets, Attack card) {
		this.source = source;
		this.targets = new HashSet<>(targets);
		if (source.isWineEffective()) {
			this.damageAmount = 2;
			this.source.resetWineEffective();
		} else {
			this.damageAmount = 1;
		}
		if (card != null) {
			this.attack = card;
		} else {
			this.attack = new Attack();
		}
	}
	
	public AttackGameController(PlayerCompleteServer source, PlayerCompleteServer target, Attack card) {
		this(source, Set.of(target), card);
	}

	@Override
	protected void handleStage(GameInternal game, AttackStage stage) throws GameFlowInterruptedException {
		switch (stage) {
			case PRE_TARGET_ACQUISITION_WEAPON_ABILITIES:
				this.nextStage();
				game.emit(new AttackPreAcquisitionWeaponAbilitiesCheckEvent(this.source, this));
				break;
			case TARGET_ACQUISITION:
				// Currently no item or hero skill affecting Target Acquisition
				this.nextStage();
				break;
			case ATTACK_RESOLUTION:
				PlayerCompleteServer currentTarget = null;
				// TODO sanity check that targets do not contain source
				for (PlayerCompleteServer next = game.getNextPlayerAlive(source); next != source; next = game.getNextPlayerAlive(next)) {
					if (this.targets.contains(next)) {
						this.targets.remove(next);
						currentTarget = next;
						break;
					}
				}
				if (currentTarget == null) {
					this.nextStage();
				} else {
					game.pushGameController(new AttackResolutionGameController(this.source, currentTarget, this.attack, this.damageAmount));
				}
				break;
			case END:
				break;
		}
	}

	public Attack getAttackCard() {
		return this.attack;
	}
	
	public void setAttackCard(Attack card) {
		this.attack = card;
	}

	@Override
	protected AttackStage getInitialStage() {
		return AttackStage.PRE_TARGET_ACQUISITION_WEAPON_ABILITIES;
	}
	
}
