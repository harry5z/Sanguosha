package core.server.game.controllers;

import cards.basics.Attack;
import core.event.game.basic.AttackEvent;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.game.Damage;
import core.server.game.Game;
import core.server.game.controllers.interfaces.DodgeUsableGameController;
import exceptions.server.game.GameFlowInterruptedException;
import utils.EnumWithNextStage;

public class AttackGameController extends AbstractGameController implements DodgeUsableGameController {

	public static enum AttackStage implements EnumWithNextStage<AttackStage> {
		TARGET_LOCKED,
		AFTER_TARGET_LOCKED_SKILLS,
		AFTER_TARGET_LOCKED_WEAPONS,
		DODGE,
		ATTACK_DODGED_WEAPONS,
		DAMAGE,
		END;
	}
	
	private AttackStage stage;
	private PlayerCompleteServer source;
	private PlayerCompleteServer target;
	private Damage damage;
	private final Attack attack;
	
	public AttackGameController(PlayerInfo source, PlayerInfo target, Attack card, Game game) {
		super(game);
		this.stage = AttackStage.TARGET_LOCKED;
		this.source = game.findPlayer(source);
		this.target = game.findPlayer(target);
		this.damage = new Damage(this.source, this.target);
		if (card != null) {
			this.damage.setElement(card.getElement());
			this.attack = card;
		} else {
			this.attack = new Attack();
		}
	}
	
	public void setStage(AttackStage stage) {
		this.stage = stage;
	}
	
	@Override
	public void proceed() {
		switch (stage) {
			case TARGET_LOCKED:
				if (source.isWineEffective()) {
					this.damage.setAmount(this.damage.getAmount() + 1);
					this.source.resetWineEffective();
				}
				
				try {
					this.game.emit(new AttackEvent(this.target.getPlayerInfo(), this.attack, this));
					this.stage = this.stage.nextStage();
					this.proceed();
				} catch (GameFlowInterruptedException e) {
					e.resume();
				}
				break;
			case AFTER_TARGET_LOCKED_SKILLS:
				stage = stage.nextStage();
				proceed();
				break;
			case AFTER_TARGET_LOCKED_WEAPONS:
				stage = stage.nextStage();
				proceed();
				break;
			case DODGE:
				this.game.pushGameController(new DodgeGameController(this.game, this.target));
				this.game.getGameController().proceed();
				break;
			case ATTACK_DODGED_WEAPONS:
				stage = AttackStage.END;
				proceed();
				break;
			case DAMAGE:
				game.pushGameController(new DamageGameController(damage, game));
				stage = stage.nextStage();
				game.getGameController().proceed();
				break;
			case END:
				source.clearDisposalArea();
				target.clearDisposalArea();
				this.onUnloaded();
				this.game.getGameController().proceed();
				break;
		}
	}

	@Override
	public void onDodged() {
		this.stage = AttackStage.ATTACK_DODGED_WEAPONS;
	}

	@Override
	public void onNotDodged() {
		this.stage = AttackStage.DAMAGE;
	}

}
