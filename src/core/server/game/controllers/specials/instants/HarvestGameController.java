package core.server.game.controllers.specials.instants;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import cards.Card;
import commands.game.client.ShowHarvestCardSelectionPaneUIClientCommand;
import core.event.game.instants.AOETargetEffectivenessEvent;
import core.event.game.instants.GenericAOEInstantSpecialTargetEffectivenessEvent;
import core.player.PlayerCardZone;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import core.server.game.controllers.CardSelectableGameController;
import core.server.game.controllers.mechanics.ReceiveCardsGameController;
import core.server.game.controllers.mechanics.RecycleCardsGameController;
import exceptions.server.game.GameFlowInterruptedException;

public class HarvestGameController extends AbstractMultiTargetInstantSpecialGameController implements CardSelectableGameController {

	private Map<Card, Boolean> selectableCards;

	public HarvestGameController(PlayerCompleteServer source, Queue<PlayerCompleteServer> targets) {
		super(source, targets);
	}
	
	@Override
	protected void takeEffect(Game game) throws GameFlowInterruptedException {
		game.getConnectionController().sendCommandToAllPlayers(
			new ShowHarvestCardSelectionPaneUIClientCommand(
				this.currentTarget.getPlayerInfo(),
				new HashMap<>(this.selectableCards)
			)
		);
		throw new GameFlowInterruptedException();
	}
	
	@Override
	protected String getNullificationMessage() {
		return this.currentTarget + " will select a card from Harvest, use Nullification?";
	}

	@Override
	public void onCardSelected(Game game, Card card, PlayerCardZone zone) {
		this.nextStage();
		// TODO: sanity check
		this.selectableCards.replace(card, true);
		game.pushGameController(new ReceiveCardsGameController(this.currentTarget, Set.of(card)));
		
		// for client UI update only, won't cause interruption
		game.getConnectionController().sendCommandToAllPlayers(
			new ShowHarvestCardSelectionPaneUIClientCommand(null, new HashMap<>(this.selectableCards))
		);
	}

	@Override
	protected AOETargetEffectivenessEvent getTargetEffectivenessEvent() {
		return new GenericAOEInstantSpecialTargetEffectivenessEvent(this.currentTarget, this);
	}
	
	@Override
	protected void onLoaded(Game game) {
		this.selectableCards = game.getDeck().drawMany(game.getNumberOfPlayersAlive()).stream().collect(Collectors.toMap(card -> card, card -> false));
		// for client UI update only, won't cause interruption
		game.getConnectionController().sendCommandToAllPlayers(
			new ShowHarvestCardSelectionPaneUIClientCommand(null, new HashMap<>(this.selectableCards))
		);
	}

	@Override
	protected void onSettled(Game game) {
		// for client UI update only, won't cause interruption
		game.getConnectionController().sendCommandToAllPlayers(new ShowHarvestCardSelectionPaneUIClientCommand(null, null));
		// discard all unselected cards
		Set<Card> unselected = this.selectableCards.entrySet().stream().filter(entry -> !entry.getValue()).map(entry -> entry.getKey()).collect(Collectors.toSet());
		if (unselected.size() > 0) {
			game.pushGameController(new RecycleCardsGameController(this.source, unselected));
		}
	}

}
