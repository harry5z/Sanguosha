package core.client.game.operations.basics;

import java.util.stream.Collectors;

import cards.basics.Attack;
import commands.server.ingame.InGameServerCommand;
import commands.server.ingame.InitiateAttackInGameServerCommand;
import core.client.game.event.InitiateAttackClientGameEvent;
import core.client.game.operations.AbstractCardInitiatedMultiTargetOperation;
import core.client.game.operations.TargetEditableOperation;
import core.player.PlayerSimple;
import ui.game.interfaces.Activatable;

public class InitiateAttackOperation extends AbstractCardInitiatedMultiTargetOperation implements TargetEditableOperation {

	public InitiateAttackOperation(Activatable activator) {
		super(activator, 1);
	}
	
	@Override
	public void onLoadedCustom() {
		super.onLoadedCustom();
		this.panel.emit(new InitiateAttackClientGameEvent(true, this));
		this.panel.getGameUI().setMessage(getMessage()); // May need to refresh Message
	}
	
	@Override
	public void onUnloadedCustom() {
		super.onUnloadedCustom();
		this.panel.emit(new InitiateAttackClientGameEvent(false, this));
	}
	
	@Override
	protected boolean isConfirmEnabled() {
		// Attack can be initiated as long as there is at least 1 target
		return this.targets.size() > 0;
	}

	@Override
	protected boolean isPlayerActivatable(PlayerSimple player) {
		if (this.getSelf().equals(player)) {
			return false;
		}
		return this.getSelf().isPlayerInAttackRange(player, this.panel.getGameState().getNumberOfPlayersAlive());
	}

	@Override
	protected String getMessage() {
		return "Select up to " + this.maxTargets + " target for Attack";
	}

	@Override
	protected InGameServerCommand getCommandOnConfirm() {
		return new InitiateAttackInGameServerCommand(
			this.targets.stream().map(target -> target.getPlayer().getPlayerInfo()).collect(Collectors.toSet()),
			(Attack) this.activator.getCard()
		);
	}

	@Override
	public void addMaxTargets(int num) {
		this.maxTargets += num;
	}

}
