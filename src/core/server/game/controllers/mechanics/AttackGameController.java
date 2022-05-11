package core.server.game.controllers.mechanics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import cards.basics.Attack;
import core.event.game.basic.AttackPreAcquisitionWeaponAbilitiesCheckEvent;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
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
	
	private PlayerCompleteServer source;
	private List<PlayerCompleteServer> targets;
	private int currentTargetIndex;
	private int damageAmount;
	private Attack attack;
	
	public AttackGameController(PlayerCompleteServer source, Collection<PlayerCompleteServer> targets, Attack card, Game game) {
		super(game);
		this.source = source;
		this.targets = new ArrayList<>();
		for (PlayerCompleteServer next = game.getNextPlayerAlive(source); next != source; next = game.getNextPlayerAlive(next)) {
			if (targets.contains(next)) {
				this.targets.add(next);
			}
		}
		this.currentTargetIndex = 0;
		this.damageAmount = 1;
		if (source.isWineEffective()) {
			this.damageAmount++;
			this.source.resetWineEffective();
		}
		if (card != null) {
			this.attack = card;
		} else {
			this.attack = new Attack();
		}
	}
	
	public AttackGameController(PlayerCompleteServer source, PlayerCompleteServer target, Attack card, Game game) {
		this(source, Set.of(target), card, game);
	}

	@Override
	protected void handleStage(AttackStage stage) throws GameFlowInterruptedException {
		switch (stage) {
			case PRE_TARGET_ACQUISITION_WEAPON_ABILITIES:
				this.nextStage();
				this.game.emit(new AttackPreAcquisitionWeaponAbilitiesCheckEvent(this.source, this));
				break;
			case TARGET_ACQUISITION:
				// Currently no item or hero skill affecting Target Acquisition
				this.nextStage();
				break;
			case ATTACK_RESOLUTION:
				PlayerCompleteServer currentTarget = this.targets.get(this.currentTargetIndex);
				this.game.pushGameController(new AttackResolutionGameController(this.source, currentTarget, this.attack, this.damageAmount, this.game));
				this.currentTargetIndex++;
				if (this.currentTargetIndex == this.targets.size()) {
					this.currentTargetIndex = 0;
					this.nextStage();
				}
				break;
			case END:
				break;
		}
	}
	
	public void setStage(AttackStage stage) {
		this.currentStage = stage;
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
