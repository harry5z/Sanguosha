package core.server.game.controllers;

import cards.Card;
import cards.basics.Attack;
import cards.basics.Dodge;
import core.event.game.basic.AttackEvent;
import core.event.game.basic.RequestDodgeEvent;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.GameRoom;
import core.server.game.Damage;
import core.server.game.controllers.interfaces.DodgeUsableGameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.InvalidPlayerCommandException;
import utils.EnumWithNextStage;

public class AttackGameController extends AbstractGameController implements DodgeUsableGameController {

	public static enum AttackStage implements EnumWithNextStage<AttackStage> {
		TARGET_SELECTION, // client side
		BEFORE_TARGET_LOCKED, // client side
		TARGET_LOCKED,
		AFTER_TARGET_LOCKED_SKILLS,
		AFTER_TARGET_LOCKED_WEAPONS,
		DODGE_DECISION, // Taichi Formation
		USING_DODGE,
		AFTER_USING_DODGE,
		ATTACK_DODGED_SKILLS,
		ATTACK_DODGED_WEAPONS,
		ATTACK_NOT_DODGED_PREVENTION,
		ATTACK_NOT_DODGED_ADDITION,
		BEFORE_DAMAGE,
		DAMAGE,
		END;
	}
	
	private AttackStage stage;
	private PlayerCompleteServer source;
	private PlayerCompleteServer target;
	private Damage damage;
	private final Attack attack;
	
	public AttackGameController(PlayerInfo source, PlayerInfo target, Attack card, GameRoom room) {
		super(room.getGame());
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
	public void onDodgeUsed(Card card) {
		try {
			if (!(card instanceof Dodge) || !target.getCardsOnHand().contains(card)) {
				throw new InvalidPlayerCommandException("Card is not dodge or target does not have this card");
			}
			target.useCard(card);
		} catch (InvalidPlayerCommandException e) {
			try {
				target.setAttackUsed(target.getAttackUsed() - 1);
			} catch (InvalidPlayerCommandException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return;
		}
		stage = AttackStage.AFTER_USING_DODGE;
		proceed();
	}
	
	@Override
	public void onDodgeNotUsed() {
		// target gives up reacting
		stage = AttackStage.ATTACK_NOT_DODGED_PREVENTION;
		proceed();
	}

	@Override
	public void proceed() {
		switch (stage) {
			case TARGET_SELECTION:
				break;
			case BEFORE_TARGET_LOCKED:
				break;
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
			case DODGE_DECISION:
				stage = stage.nextStage();
				proceed();
				break;
			case USING_DODGE:
				try {
					this.game.emit(new RequestDodgeEvent(this.target.getPlayerInfo()));
				} catch (GameFlowInterruptedException e) {
					// Do nothing
				}
				break;
			case AFTER_USING_DODGE:
				stage = stage.nextStage();
				proceed();
				break;
			case ATTACK_DODGED_SKILLS:
				stage = stage.nextStage();
				proceed();
				break;
			case ATTACK_DODGED_WEAPONS:
				stage = AttackStage.END;
				proceed();
				break;
			case ATTACK_NOT_DODGED_PREVENTION:
				stage = stage.nextStage();
				proceed();
				break;
			case ATTACK_NOT_DODGED_ADDITION:
				stage = stage.nextStage();
				proceed();
				break;
			case BEFORE_DAMAGE:
				stage = stage.nextStage();
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
				this.onCompleted();
				break;
		}
	}

}
