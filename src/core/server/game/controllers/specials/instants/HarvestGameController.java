package core.server.game.controllers.specials.instants;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import cards.Card;
import commands.game.client.ShowHarvestCardSelectionPaneUIClientCommand;
import commands.game.client.sync.ui.SyncHarvestCardSelectionPaneGameUIClientCommand;
import core.event.game.instants.AOETargetEffectivenessEvent;
import core.event.game.instants.GenericAOEInstantSpecialTargetEffectivenessEvent;
import core.player.PlayerCardZone;
import core.player.PlayerCompleteServer;
import core.server.game.GameInternal;
import core.server.game.controllers.CardSelectableGameController;
import core.server.game.controllers.mechanics.ReceiveCardsGameController;
import core.server.game.controllers.mechanics.RecycleCardsGameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.IllegalPlayerActionException;

public class HarvestGameController extends AbstractMultiTargetInstantSpecialGameController implements CardSelectableGameController {

	private Map<Card, Boolean> selectableCards;

	public HarvestGameController(PlayerCompleteServer source, Queue<PlayerCompleteServer> targets) {
		super(source, targets);
	}
	
	@Override
	protected void takeEffect(GameInternal game) throws GameFlowInterruptedException {
		throw new GameFlowInterruptedException(
			new ShowHarvestCardSelectionPaneUIClientCommand(
				this.currentTarget.getPlayerInfo(),
				new HashMap<>(this.selectableCards)
			)
		);
	}
	
	@Override
	protected String getNullificationMessage() {
		return this.currentTarget + " will select a card from Harvest, use Nullification?";
	}

	@Override
	public void onCardSelected(GameInternal game, Card card, PlayerCardZone zone) {
		this.nextStage();
		this.selectableCards.replace(card, true);
		game.pushGameController(new ReceiveCardsGameController(this.currentTarget, Set.of(card)));
		
		// for client UI update only, won't cause interruption
		game.getSyncController().sendSyncCommandToAllPlayers(
			new SyncHarvestCardSelectionPaneGameUIClientCommand(new HashMap<>(this.selectableCards))
		);
	}

	@Override
	public void validateCardSelected(GameInternal game, Card card, PlayerCardZone zone) throws IllegalPlayerActionException {
		if (card == null) {
			throw new IllegalPlayerActionException("Harvest: Card cannot be null");
		}
		if (!selectableCards.containsKey(card)) {
			throw new IllegalPlayerActionException("Harvest: Card is not in selectable cards");
		}
		if (selectableCards.get(card)) {
			throw new IllegalPlayerActionException("Harvest: Card already selected");
		}
	}

	@Override
	protected AOETargetEffectivenessEvent getTargetEffectivenessEvent() {
		return new GenericAOEInstantSpecialTargetEffectivenessEvent(this.currentTarget, this);
	}
	
	@Override
	protected void onLoaded(GameInternal game) {
		this.selectableCards = game.getDeck().drawMany(game.getNumberOfPlayersAlive()).stream().collect(Collectors.toMap(card -> card, card -> false));
		// for client UI update only, won't cause interruption
		game.getSyncController().sendSyncCommandToAllPlayers(
			new SyncHarvestCardSelectionPaneGameUIClientCommand(new HashMap<>(this.selectableCards))
		);
	}

	@Override
	protected void onSettled(GameInternal game) {
		// for client UI update only, won't cause interruption
		game.getSyncController().sendSyncCommandToAllPlayers(new SyncHarvestCardSelectionPaneGameUIClientCommand(null));
		// discard all unselected cards
		Set<Card> unselected = this.selectableCards.entrySet().stream().filter(entry -> !entry.getValue()).map(entry -> entry.getKey()).collect(Collectors.toSet());
		if (unselected.size() > 0) {
			game.pushGameController(new RecycleCardsGameController(this.source, unselected));
		}
	}

}
