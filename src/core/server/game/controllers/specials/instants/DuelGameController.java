package core.server.game.controllers.specials.instants;

import cards.Card;
import cards.basics.Attack;
import core.event.game.basic.RequestAttackEvent;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.game.Damage;
import core.server.game.Game;
import core.server.game.controllers.DamageGameController;
import core.server.game.controllers.interfaces.AttackUsableGameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.InvalidPlayerCommandException;

public class DuelGameController extends SingleTargetInstantSpecialGameController implements AttackUsableGameController {
	
	private PlayerCompleteServer currentAttackUser;
	
	public DuelGameController(PlayerInfo source, PlayerInfo target, Game game) {
		super(source, target, game);
		this.currentAttackUser = this.target;
	}

	@Override
	protected void takeEffect() {
		try {
			// Ask current attack user to use Attack
			this.game.emit(new RequestAttackEvent(
				this.currentAttackUser.getPlayerInfo(),
				"You are in a Duel against " +
				(this.currentAttackUser == this.target ? this.source : this.target) +
				", it's your turn to use Attack."
			));
		} catch (GameFlowInterruptedException e) {
			e.resume();
		}
	}
	

	@Override
	protected String getNeutralizationMessage() {
		return this.source + " used Duel on " + this.target + ", use Neutralization?";
	}

	@Override
	public void onAttackUsed(Card card) {
		try {
			if (!(card instanceof Attack) || !this.currentAttackUser.getCardsOnHand().contains(card)) {
				throw new InvalidPlayerCommandException("Card is not attack or target does not have this card");
			}
			this.currentAttackUser.useCard(card);
		} catch (InvalidPlayerCommandException e) {
			e.printStackTrace();
			return;
		}
		// change attack user and continue
		this.currentAttackUser = this.currentAttackUser == this.target ? this.source : this.target;
	}

	@Override
	public void onAttackNotUsed() {
		this.stage = this.stage.nextStage();
		// current attack user takes 1 damage from the other player
		PlayerCompleteServer damageTarget = this.currentAttackUser;
		PlayerCompleteServer damageSource = damageTarget == this.target ? this.source : this.target;
		this.game.pushGameController(new DamageGameController(new Damage(damageSource, damageTarget), this.game));
	}

}
