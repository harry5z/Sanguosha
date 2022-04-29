package ui.game.custom;

import java.awt.Color;
import java.awt.Font;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JLabel;
import javax.swing.JPanel;

import cards.Card;
import core.client.GamePanel;
import ui.game.CardGui;
import ui.game.GamePanelGui;
import ui.game.interfaces.CardUI;

public class HarvestSelectionPane extends JPanel {

	private static final long serialVersionUID = -1L;
	private static final int LABEL_HEIGHT = 40;

	public HarvestSelectionPane(Map<Card, Boolean> selectableCards, String targetName, GamePanel panel) {
		setBackground(new Color(222, 184, 135));
		int size = selectableCards.size();
		int width = CardGui.WIDTH * (size > 4 ? 4 : size) + GamePanelGui.WIDTH / 4;
		int height = LABEL_HEIGHT + CardGui.HEIGHT * ((size - 1) / 4 + 1);
		setSize(width, height);
		setLayout(null);
		
		if (targetName != null) {
			JLabel label = new JLabel("Harvest: Waiting for " + targetName + " to select a card.");
			label.setHorizontalAlignment(JLabel.CENTER);
			label.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
			label.setSize(width, LABEL_HEIGHT);
			add(label);
		}

		JPanel cardPanel = new JPanel();
		cardPanel.setLayout(null);
		cardPanel.setSize(width - GamePanelGui.WIDTH / 4, height - LABEL_HEIGHT);
		cardPanel.setLocation(GamePanelGui.WIDTH / 8, LABEL_HEIGHT);
		int x = 0;
		int y = 0;
		for (Entry<Card, Boolean> entry : selectableCards.entrySet()) {
			CardGui gui = new CardGui(entry.getKey());
			gui.setLocation(x, y);
			x += CardGui.WIDTH;
			if (x == 4 * CardGui.WIDTH) {
				x = 0;
				y += CardGui.HEIGHT;
			}
			
			// disable selected cards
			gui.setActivatable(!entry.getValue());
			
			if (panel != null) {
				gui.addActionListener(e -> panel.getCurrentOperation().onCardClicked((CardUI) e.getSource()));
			}
			
			cardPanel.add(gui);
		}
		add(cardPanel);
	}
}
