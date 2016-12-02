package cards.equipments.shields;

import cards.Card;
import cards.basics.Attack;
import cards.specials.instant.ArrowSalvo;
import cards.specials.instant.BarbarianInvasion;
import core.event.handlers.damage.RattenArmorCheckDamageEventHandler;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import core.server.game.Damage.Element;

public class RattanArmor extends Shield {

	private static final long serialVersionUID = -4377220192386216241L;

	public RattanArmor(int num, Suit suit, int id) {
		super(num, suit, id);
	}

	@Override
	public String getName() {
		return "Rattan Armor";
	}

	@Override
	public boolean mustReactTo(Card card) {
		if (card instanceof ArrowSalvo || card instanceof BarbarianInvasion) {
			return false;
		} else if (card instanceof Attack && ((Attack) card).getElement() == Element.NORMAL) {
			return false;
		} else {
			return true;
		}
	}
	
	@Override
	public void onEquipped(Game game, PlayerCompleteServer owner) {
		game.registerEventHandler(new RattenArmorCheckDamageEventHandler(owner));
	}
	
	@Override
	public void onUnequipped(Game game, PlayerCompleteServer owner) {
		game.removeEventHandler(new RattenArmorCheckDamageEventHandler(owner));
	}

}
