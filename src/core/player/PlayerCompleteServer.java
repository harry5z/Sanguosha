package core.player;

import cards.Card;
import core.event.handlers.basic.RequestAttackEventHandler;
import core.event.handlers.basic.RequestDodgeEventHandler;
import core.event.handlers.equipment.EquipEventHandler;
import core.event.handlers.equipment.UnequipEventHandler;
import core.event.handlers.turn.DealTurnEventHandler;
import core.event.handlers.turn.DiscardTurnEventHandler;
import core.event.handlers.turn.DrawTurnEventHandler;
import core.server.game.Game;
import exceptions.server.game.InvalidPlayerCommandException;
import listeners.game.CardDisposalListener;
import listeners.game.CardOnHandListener;

public class PlayerCompleteServer extends PlayerComplete {

	public PlayerCompleteServer(String name, int position) {
		super(name, position);
	}

	@Override
	public void useAttack() throws InvalidPlayerCommandException {
		if (getAttackUsed() >= getAttackLimit()) {
			throw new InvalidPlayerCommandException("Attack used exceeds attack limit");
		}
		super.useAttack();
	}
	
	@Override
	public void useWine() throws InvalidPlayerCommandException {
		if (getWineUsed() >= getWineLimit()) {
			throw new InvalidPlayerCommandException("Wine used exceeds wine limit");
		}
		super.useWine();
	}
	
	@Override
	public void setAttackLimit(int limit) throws InvalidPlayerCommandException {
		if (limit < 0) {
			throw new InvalidPlayerCommandException("Attack limit cannot be smaller than 0");
		}
		super.setAttackLimit(limit);
	}
	
	@Override
	public void setAttackUsed(int amount) throws InvalidPlayerCommandException {
		if (amount < 0) {
			throw new InvalidPlayerCommandException("Attack used cannot be smaller than 0");
		}
		super.setAttackUsed(amount);
	}
	
	@Override
	public void setWineUsed(int amount) throws InvalidPlayerCommandException {
		if (amount < 0) {
			throw new InvalidPlayerCommandException("Wine used cannot be smaller than 0");
		}
		super.setWineUsed(amount);
	}
	
	@Override
	protected void cardOnHandListenerAction(CardOnHandListener listener, Card card) {
		// Do nothing on server side
	}
	
	@Override
	protected void cardDisposalListenerAction(CardDisposalListener listener, Card card) {
		// Do nothing on server side
	}
	
	public void onGameReady(Game game) {
		/* setup event listeners */
		game.registerEventHandler(new DealTurnEventHandler(this));
		game.registerEventHandler(new DrawTurnEventHandler(this));
		game.registerEventHandler(new DiscardTurnEventHandler(this));
		game.registerEventHandler(new RequestDodgeEventHandler(this));
		game.registerEventHandler(new RequestAttackEventHandler(this));
		game.registerEventHandler(new UnequipEventHandler(this));
		game.registerEventHandler(new EquipEventHandler(this));
	}
}
