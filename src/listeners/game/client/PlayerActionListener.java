package listeners.game.client;

import ui.game.CardGui;
import ui.game.EquipmentGui;
import ui.game.HeroGui;
import ui.game.PlayerGui;

public interface PlayerActionListener {

	default public void onCardClicked(CardGui card) {}
	
	default public void onPlayerClicked(PlayerGui player) {}
	
	default public void onSelfClicked(HeroGui self) {}
	
	default public void onEquipmentClicked(EquipmentGui equipment) {}
	
	default public void onConfirmed() {}
	
	default public void onCanceled() {}
	
	default public void onEnded() {}
}
