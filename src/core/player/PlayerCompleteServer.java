package core.player;

import cards.Card;
import core.event.DealTurnEventHandler;
import core.event.DrawTurnEventHandler;
import core.server.game.Game;
import core.server.game.controllers.DiscardTurnEventHandler;
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
		
	}
	
	@Override
	protected void cardDisposalListenerAction(CardDisposalListener listener, Card card) {
		
	}
	
	public void onGameReady(Game game) {
		/* setup event listeners */
		game.registerEventHandler(new DealTurnEventHandler(this));
		game.registerEventHandler(new DrawTurnEventHandler(this));
		game.registerEventHandler(new DiscardTurnEventHandler(this));
	}
}
