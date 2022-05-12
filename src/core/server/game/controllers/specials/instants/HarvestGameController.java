package core.server.game.controllers.specials.instants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import cards.Card;
import core.event.game.instants.AOETargetEffectivenessEvent;
import core.event.game.instants.GenericAOEInstantSpecialTargetEffectivenessEvent;
import core.event.game.instants.HarvestCardSelectionEvent;
import core.event.handlers.instant.HarvestCardSelectionEventHandler;
import core.player.PlayerCardZone;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import core.server.game.controllers.CardSelectableGameController;
import core.server.game.controllers.mechanics.ReceiveCardsGameController;
import core.server.game.controllers.mechanics.RecycleCardsGameController;
import exceptions.server.game.GameFlowInterruptedException;

public class HarvestGameController extends AbstractMultiTargetInstantSpecialGameController implements CardSelectableGameController {

	private Map<Card, Boolean> selectableCards;
	private List<PlayerCompleteServer> players;

	public HarvestGameController(PlayerCompleteServer source, Queue<PlayerCompleteServer> targets, Collection<Card> selectableCards) {
		super(source, targets);
		this.selectableCards = selectableCards.stream().collect(Collectors.toMap(card -> card, card -> false));
		this.players = new ArrayList<>(targets);
	}
	
	@Override
	protected void takeEffect(Game game) throws GameFlowInterruptedException {
		game.emit(new HarvestCardSelectionEvent(this.currentTarget.getPlayerInfo(), new HashMap<>(this.selectableCards)));
		throw new GameFlowInterruptedException();
	}
	
	@Override
	protected String getNeutralizationMessage() {
		return this.currentTarget + " will select a card from Harvest, use Neutralization?";
	}

	@Override
	public void onCardSelected(Game game, Card card, PlayerCardZone zone) {
		this.nextStage();
		// TODO: sanity check
		this.selectableCards.replace(card, true);
		
		// for client UI update only, won't cause interruption
		try {
			game.emit(new HarvestCardSelectionEvent(null, new HashMap<>(this.selectableCards)));
		} catch (GameFlowInterruptedException e) {
			e.resume();
		}
		
		game.pushGameController(new ReceiveCardsGameController(this.currentTarget, Set.of(card)));
	}

	@Override
	protected AOETargetEffectivenessEvent getTargetEffectivenessEvent() {
		return new GenericAOEInstantSpecialTargetEffectivenessEvent(this.currentTarget, this);
	}

	@Override
	protected void onSettled(Game game) {
		// for client UI update only, won't cause interruption
		try {
			game.emit(new HarvestCardSelectionEvent(null, null));
		} catch (GameFlowInterruptedException e) {
			e.resume();
		}
		// remove handlers
		this.players.forEach(player -> game.removeEventHandler(new HarvestCardSelectionEventHandler(player)));
		// discard all unselected cards
		Set<Card> unselected = this.selectableCards.entrySet().stream().filter(entry -> !entry.getValue()).map(entry -> entry.getKey()).collect(Collectors.toSet());
		if (unselected.size() > 0) {
			game.pushGameController(new RecycleCardsGameController(this.source, unselected));
		}
	}

}
