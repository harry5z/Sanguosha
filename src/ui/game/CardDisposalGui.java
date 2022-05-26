package ui.game;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import cards.Card;
import listeners.game.CardDisposalListener;

/**
 * Gui of card disposal area, public to all players
 * 
 * @author Harry
 *
 */
public class CardDisposalGui extends JPanel implements CardDisposalListener {

	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 600;
	public static final int HEIGHT = CardGui.HEIGHT;
	private static final int STEPS = 50;
	private static final int INTERVAL = 50;
	private final CardRackGui usage;
	private final Timer timer;
	private TimerTask task;
	private int ticks;
	private final JPanel parent;

	public CardDisposalGui(JPanel parentPanel) {
		this.parent = parentPanel;
		setLayout(null);
		setSize(WIDTH, HEIGHT);
		setLocation((GamePanelGui.WIDTH - WIDTH) / 2, (GamePanelGui.HEIGHT - HEIGHT) / 2);
		usage = new CardRackGui(null);
		usage.setSize(WIDTH, CardGui.HEIGHT);
		usage.setLocation(0, 0);
		add(usage);

		timer = new Timer();
		task = null;
		ticks = 0;
	}

	@Override
	public void onCardUsed(Card card) {
		usage.onCardAdded(card);
		parent.validate();
		parent.repaint();
	}

	@Override
	public void onCardDisposed(Card card) {
		usage.onCardAdded(card);
		parent.validate();
		parent.repaint();
	}
	
	@Override
	public void onCardShown(Card card) {
		usage.onCardAdded(card);
		parent.validate();
		parent.repaint();
	}

	@Override
	public synchronized void refresh() {
		if (task != null || usage.getCardUIs().isEmpty()) {
			return;
		}
		task = new TimerTask() {
			@Override
			public void run() {
				synchronized (CardDisposalGui.this) {
					if (ticks >= STEPS) {
						cancel();
						task = null;
						usage.clearRack();
						return;
					}
					usage.fadeCards(1f / STEPS);
					ticks++;
				}
			}
		};
		ticks = 0;
		timer.schedule(task, 0, INTERVAL);
	}

}
