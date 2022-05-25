package commands.game.client.sync.player;

import java.util.List;

import javax.swing.JOptionPane;

import core.GameState;
import core.player.Role;
import exceptions.server.game.InvalidPlayerCommandException;

public class DisplayPopupMessageGameClientCommand extends AbstractSyncPlayerClientCommand {

	private static final long serialVersionUID = 1L;
	
	private final List<Role> winners;

	public DisplayPopupMessageGameClientCommand(List<Role> winners) {
		this.winners = winners;
	}
	
	@Override
	protected void sync(GameState state) throws InvalidPlayerCommandException {
		if (winners.contains(state.getSelf().getRole())) {
			new Thread(() -> JOptionPane.showMessageDialog(null, "Game ended. You (" + state.getSelf().getRole() + ") win!")).start();;
		} else {
			String winnerText = winners.get(0).name();
			for (int i = 1; i < winners.size(); i++) {
				winnerText += " and " + winners.get(i);
			}
			String txt = winnerText;
			new Thread(() -> JOptionPane.showMessageDialog(null, "Game ended. You (" + state.getSelf().getRole() + ") Lost. " + txt + " win.")).start();
		}
		
	}


}
