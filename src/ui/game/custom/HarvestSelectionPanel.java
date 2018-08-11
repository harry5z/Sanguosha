package ui.game.custom;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import cards.Card;
import ui.game.CardGui;
import ui.game.GamePanelGui;

public class HarvestSelectionPanel extends JPanel {

	private static final long serialVersionUID = -1L;
	private static final int LABEL_HEIGHT = 40;

	public HarvestSelectionPanel(List<Card> all, List<Card> remaining, String targetName, ActionListener listener) {
		setBackground(new Color(222, 184, 135));
		int size = all.size();
		int width = CardGui.WIDTH * (size > 4 ? 4 : size) + GamePanelGui.WIDTH / 4;
		int height = LABEL_HEIGHT + CardGui.HEIGHT * ((size - 1) / 4 + 1);
		setSize(width, height);
		setLayout(null);
		JLabel label = new JLabel("Harvest: Waiting for " + targetName + " to select a card.");
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
		label.setSize(width, LABEL_HEIGHT);
		add(label);

		JPanel cardPanel = new JPanel();
		cardPanel.setLayout(null);
		cardPanel.setSize(width - GamePanelGui.WIDTH / 4, height - LABEL_HEIGHT);
		cardPanel.setLocation(GamePanelGui.WIDTH / 8, LABEL_HEIGHT);
		int x = 0;
		int y = 0;
		for (Card card : all) {
			CardGui gui = new CardGui(card);
			gui.setLocation(x, y);
			x += CardGui.WIDTH;
			if (x == 4 * CardGui.WIDTH) {
				x = 0;
				y += CardGui.HEIGHT;
			}
			if (!remaining.contains(card))
				gui.setEnabled(false);
			if (listener != null)
				gui.addActionListener(listener);
			cardPanel.add(gui);
		}
		add(cardPanel);
	}
}
