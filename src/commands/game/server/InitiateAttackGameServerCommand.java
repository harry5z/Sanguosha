package commands.game.server;

import cards.basics.Attack;
import cards.equipments.Equipment.EquipmentType;
import core.PlayerInfo;
import core.server.Game;
import core.server.game.controllers.AttackGameController;
import exceptions.server.game.InvalidPlayerCommandException;
import net.Connection;
import net.server.GameRoom;
import player.PlayerCompleteServer;

public class InitiateAttackGameServerCommand implements GameServerCommand {
	
	private static final long serialVersionUID = -4460787768760646177L;

	private final PlayerInfo source;
	private final PlayerInfo target;
	private final Attack attack;
	
	public InitiateAttackGameServerCommand(PlayerInfo source, PlayerInfo target, Attack attack) {
		this.source = source;
		this.target = target;
		this.attack = attack;
	}

	@Override
	public void execute(GameRoom room, Connection connection) {
		Game game = room.getGame();
		PlayerCompleteServer player = game.findPlayer(source);
		try {
			player.useAttack();
		} catch (InvalidPlayerCommandException e) {
			e.printStackTrace();
			return;
		}
		if (attack != null) {
			try {
				player.useCard(attack);
			} catch (InvalidPlayerCommandException e) {
				try {
					player.setAttackUsed(player.getAttackUsed() - 1);
				} catch (InvalidPlayerCommandException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
				return;
			}
		}
		game.pushGameController(new AttackGameController(source, target, attack, room));
		game.getGameController().proceed();
	}

}
