package ui.game;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import core.Constants;
import ui.client.components.LabelGui;

@SuppressWarnings("serial")
public class BattleLogGui extends JPanel {
	
	public static final int WIDTH = 300;
	public static final int HEIGHT = GamePanelGui.HEIGHT;
	
	private String logs = "";
	private final JTextPane logArea;
	
	public BattleLogGui() {
		setLayout(null);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		JLabel label = new LabelGui("Battle Log");
		label.setSize(WIDTH, 50);
		logArea = new JTextPane();
		logArea.setContentType("text/html");
		logArea.setEditable(false);
		logArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
		logArea.setSize(WIDTH, HEIGHT - 50);
		logArea.setLocation(0, 50);
		JScrollPane scroll = new JScrollPane(logArea);
		scroll.setSize(WIDTH, HEIGHT - 50);
		scroll.setLocation(0, 50);
		add(label);
		add(scroll);
	}
	
	public void pushBattleLog(String log) {
		logs = "<div style=\"margin: 5px 0 5px 0\">" + log + "</div>" + logs;
		logArea.setText("<html>" + logs + "</html>");
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		((Graphics2D) g).setStroke(new BasicStroke(Constants.BORDER_WIDTH));
		g.drawRect(0, 0, WIDTH, HEIGHT);
	}

}
