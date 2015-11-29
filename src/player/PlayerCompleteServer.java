package player;

import cards.Card;
import core.server.game.controllers.GameController;
import exceptions.server.game.InvalidPlayerCommandException;
import listeners.game.CardDisposalListener;
import listeners.game.CardOnHandListener;
import listeners.game.PlayerStatusListener;

public class PlayerCompleteServer extends PlayerComplete {

	
	public PlayerCompleteServer(String name, int position) {
		super(name, position);
	}

	public boolean makeAction(GameController controller) {
		for (Card card : this.getCardsOnHand()) {
			// if card has action
		}
		// if (this.hero.makeAction(controller)) ...
		return false;
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
}
