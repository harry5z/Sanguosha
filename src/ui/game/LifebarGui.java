package ui.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

import listeners.game.HealthListener;

/**
 * life bar. current health is represented as green dots, lost health is
 * represented as red dots
 * 
 * @author Harry
 *
 */
public class LifebarGui extends JPanel implements HealthListener {
	
	private static final long serialVersionUID = -4065203819976238788L;
	
	public static final int WIDTH = 50;
	public static final int HEIGHT = CardRackGui.HEIGHT;
	private int limit;
	private int current;
	private boolean alive;

	public LifebarGui() {
		setSize(WIDTH, HEIGHT);
		setLocation(GamePanelGui.WIDTH - WIDTH, GamePanelGui.HEIGHT - HEIGHT);
		setLayout(null);
		limit = 0;
		current = 0;
		alive = true;
	}

	@Override
	public synchronized void onSetHealthLimit(int limit) {
		this.limit = limit;
		repaint();
	}

	@Override
	public synchronized void onSetHealthCurrent(int current) {
		this.current = current;
		repaint();
	}

	@Override
	public synchronized void onHealthChangedBy(int amount) {
		current += amount;
		if (current > limit)// may not be necessary
			current = limit;
		repaint();
	}

	@Override
	public synchronized void onDeath() {
		alive = false;
		JLabel death = new JLabel("<HTML>D<br />E<br />A<br />D</HTML>");
		death.setSize(WIDTH, HEIGHT);
		death.setHorizontalAlignment(JLabel.CENTER);
		death.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
		add(death);
		revalidate();
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (!alive) {
			return;
		}
		g.drawRect(0, 0, WIDTH, HEIGHT);
		if (limit == 0)
			return;
		int diameter = HEIGHT / (limit * 2 + 1);
		int x = (WIDTH - diameter) / 2;
		for (int i = 0; i < limit; i++) {
			if (i < current)
				g.setColor(Color.GREEN);
			else
				g.setColor(Color.RED);
			g.fillOval(x, (2 * i + 1) * diameter, diameter, diameter);
		}
	}

}
