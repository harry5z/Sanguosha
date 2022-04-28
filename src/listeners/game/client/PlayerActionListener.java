package listeners.game.client;

import ui.game.interfaces.CardUI;
import ui.game.interfaces.EquipmentUI;
import ui.game.interfaces.PlayerUI;
import ui.game.interfaces.SkillUI;

public interface PlayerActionListener {

	default public void onCardClicked(CardUI card) {}
	
	default public void onPlayerClicked(PlayerUI player) {}
	
	default public void onEquipmentClicked(EquipmentUI equipment) {}
	
	default public void onDelayedClicked(CardUI card) {}
	
	default public void onSkillClicked(SkillUI skill) {}
	
	default public void onConfirmed() {}
	
	default public void onCanceled() {}
	
	default public void onEnded() {}
}
