package listeners.game.client;

import core.heroes.Hero;
import ui.game.interfaces.CardUI;
import ui.game.interfaces.EquipmentUI;
import ui.game.interfaces.HeroUI;
import ui.game.interfaces.PlayerUI;

public interface PlayerActionListener {

	default public void onCardClicked(CardUI card) {}
	
	default public void onPlayerClicked(PlayerUI player) {}
	
	default public void onSelfClicked(HeroUI<? extends Hero> self) {}
	
	default public void onEquipmentClicked(EquipmentUI equipment) {}
	
	default public void onConfirmed() {}
	
	default public void onCanceled() {}
	
	default public void onEnded() {}
}
