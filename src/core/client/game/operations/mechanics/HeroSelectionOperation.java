package core.client.game.operations.mechanics;

import java.util.List;

import commands.server.ingame.HeroSelectionInGameServerCommand;
import core.client.game.operations.AbstractOperation;
import core.heroes.Hero;
import ui.game.custom.HeroSelectionPane;

public class HeroSelectionOperation extends AbstractOperation {
	
	private final List<Hero> availableHeroes;
	
	public HeroSelectionOperation(List<Hero> availableHeroes) {
		this.availableHeroes = availableHeroes;
	}
	
	public void onHeroSelected(int index) {
		this.onUnloaded();
		this.onDeactivated();
		this.panel.sendResponse(new HeroSelectionInGameServerCommand(index));
	}

	@Override
	public void onLoaded() {
		this.panel.getGameUI().setMessage("Please select your hero");
		this.panel.getGameUI().displayCustomizedSelectionPaneAtCenter(new HeroSelectionPane(availableHeroes, index -> onHeroSelected(index)));
	}
	
	@Override
	public void onUnloaded() {
		this.panel.getGameUI().clearMessage();
		this.panel.getGameUI().removeSelectionPane();
	}

}
