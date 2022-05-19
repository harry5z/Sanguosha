package ui.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class CountdownBarGui extends JPanel {

	private final Timer timer;
	private int totalMS;
	private int remainingMS;
	private long startTimeMS;
	private TimerTask countdown;
	
	public CountdownBarGui(int width, int height) {
		setSize(width, height);
		timer = new Timer();
		totalMS = 0;
		remainingMS = 0;
		startTimeMS = 0;
		countdown = null;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (countdown == null) {
			return;
		}
		int width = getWidth();
		int height = getHeight();
		((Graphics2D) g).setStroke(new BasicStroke(2));
		
		g.setColor(Color.RED);
		g.fillRect(0, 0, (int)((double)width * remainingMS / totalMS), height);
		
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, width, height);
	}
	
	public void countdown(int timeMS) {
		if (countdown != null) {
			countdown.cancel();
		}
		totalMS = timeMS;
		startTimeMS = System.currentTimeMillis();
		countdown = new TimerTask() {
			@Override
			public void run() {
				remainingMS = totalMS - (int)(System.currentTimeMillis() - startTimeMS);
				if (remainingMS <= 0) {
					countdown = null;
					cancel();
				}
				repaint();
			}
		};
		timer.scheduleAtFixedRate(countdown, 0, 25);
	}
	
	public void stopCountdown() {
		if (countdown != null) {
			countdown.cancel();
			countdown = null;
			repaint();
		}
	}
}
