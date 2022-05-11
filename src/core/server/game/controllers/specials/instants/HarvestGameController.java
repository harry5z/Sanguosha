package core.server.game.controllers.specials.instants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import cards.Card;
import core.event.game.instants.AOETargetEffectivenessEvent;
import core.event.game.instants.GenericAOEInstantSpecialTargetEffectivenessEvent;
import core.event.game.instants.HarvestCardSelectionEvent;
import core.event.handlers.instant.HarvestCardSelectionEventHandler;
import core.player.PlayerCardZone;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.game.Game;
import core.server.game.controllers.CardSelectableGameController;
import core.server.game.controllers.mechanics.ReceiveCardsGameController;
import core.server.game.controllers.mechanics.RecycleCardsGameController;
import exceptions.server.game.GameFlowInterruptedException;

public class HarvestGameController extends AOEInstantSpecialGameController implements CardSelectableGameController {

	private Map<Card, Boolean> selectableCards;
	private List<PlayerCompleteServer> players;

	public HarvestGameController(PlayerInfo source, Game game) {
		super(source, game, true);
		this.selectableCards = null;
		this.players = game.getPlayersAlive();
		// register handlers
		this.players.forEach(player -> game.registerEventHandler(new HarvestCardSelectionEventHandler(player)));
		this.selectableCards = game.getDeck().drawMany(game.getNumberOfPlayersAlive()).stream().collect(Collectors.toMap(card -> card, card -> false));
		
		// for client UI update only, won't cause interruption
		try {
			this.game.emit(new HarvestCardSelectionEvent(null, new HashMap<>(this.selectableCards)));
		} catch (GameFlowInterruptedException e) {
			e.resume();
		}
	}
	
	@Override
	protected void takeEffect() throws GameFlowInterruptedException {
		this.game.emit(new HarvestCardSelectionEvent(this.currentTarget.getPlayerInfo(), new HashMap<>(this.selectableCards)));
		throw new GameFlowInterruptedException();
	}
	
	@Override
	protected String getNeutralizationMessage() {
		return this.currentTarget + " will select a card from Harvest, use Neutralization?";
	}

	@Override
	public void onCardSelected(Card card, PlayerCardZone zone) {
		this.nextStage();
		// TODO: sanity check
		this.selectableCards.replace(card, true);
		
		// for client UI update only, won't cause interruption
		try {
			this.game.emit(new HarvestCardSelectionEvent(null, new HashMap<>(this.selectableCards)));
		} catch (GameFlowInterruptedException e) {
			e.resume();
		}
		
		this.game.pushGameController(new ReceiveCardsGameController(this.game, this.currentTarget, Set.of(card)));
	}

	@Override
	protected AOETargetEffectivenessEvent getTargetEffectivenessEvent() {
		return new GenericAOEInstantSpecialTargetEffectivenessEvent(this.currentTarget, this);
	}

	@Override
	protected void onSettled() {
		// for client UI update only, won't cause interruption
		try {
			this.game.emit(new HarvestCardSelectionEvent(null, null));
		} catch (GameFlowInterruptedException e) {
			e.resume();
		}
		// remove handlers
		this.players.forEach(player -> game.removeEventHandler(new HarvestCardSelectionEventHandler(player)));
		// discard all unselected cards
		Set<Card> unselected = this.selectableCards.entrySet().stream().filter(entry -> !entry.getValue()).map(entry -> entry.getKey()).collect(Collectors.toSet());
		if (unselected.size() > 0) {
			this.game.pushGameController(new RecycleCardsGameController(this.game, this.source, unselected));
		}
	}

}
