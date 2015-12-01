package core.server.game.controllers;

import java.util.stream.Collectors;

import cards.Card;
import cards.basics.Attack;
import cards.basics.Dodge;
import cards.equipments.Equipment.EquipmentType;
import commands.game.client.RequestDodgeGameUIClientCommand;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.GameRoom;
import core.server.game.Damage;
import core.server.game.Game;
import core.server.game.Damage.Element;
import exceptions.server.game.InvalidPlayerCommandException;

public class AttackGameController implements GameController, DodgeUsableGameController {

	public enum AttackStage {
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
		
		private static final AttackStage[] VALUES = values();
		
		public AttackStage nextStage() {
			return VALUES[(this.ordinal() + 1) % VALUES.length];
		}
	}
	
	private AttackStage stage;
	private PlayerCompleteServer source;
	private PlayerCompleteServer target;
	private Damage damage;
	private final GameRoom room;
	private final Game game;
	private final Attack attack;
	
	public AttackGameController(PlayerInfo source, PlayerInfo target, Attack card, GameRoom room) {
		this.stage = AttackStage.TARGET_LOCKED;
		this.room = room;
		this.game = room.getGame();
		this.source = game.findPlayer(source);
		this.target = game.findPlayer(target);
		this.damage = new Damage(this.source, this.target);
		if (card != null) {
			this.damage.setElement(card.getElement());
			this.attack = card;
		} else {
			this.attack = new Attack(Element.NORMAL, -1, null, -1);
		}
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
				if (target.isEquipped(EquipmentType.SHIELD) && !target.getShield().mustReactTo(attack)) {
					stage = AttackStage.END;
				} else {
					stage = stage.nextStage();
				}
				proceed();
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
				final PlayerInfo targetInfo = this.target.getPlayerInfo();
				room.sendCommandToPlayers(
					game.getPlayersInfo().stream().collect(
						Collectors.toMap(
							info -> info.getName(),
							info -> new RequestDodgeGameUIClientCommand(targetInfo)
						)
					)
				);
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
				if (target.isEquipped(EquipmentType.SHIELD)) {
					target.getShield().modifyDamage(damage);
				}
				damage.apply();
				stage = stage.nextStage();
				proceed();
				break;
			case END:
				source.clearDisposalArea();
				target.clearDisposalArea();
				game.popGameController();
				game.getGameController().proceed();
				break;
			default:
				break;
		}
	}

}
