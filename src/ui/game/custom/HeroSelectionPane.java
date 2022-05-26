package ui.game.custom;

import java.awt.Color;
import java.awt.Font;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JPanel;

import core.heroes.Hero;

public class HeroSelectionPane extends JPanel {

	private static final long serialVersionUID = -1L;
	private static final int BUTTON_WIDTH = 200;
	private static final int BUTTON_HEIGHT = 300;
	private static final int HORIZONTAL_GAP = 50;
	private static final int VERTICAL_GAP = 50;

	public HeroSelectionPane(List<Hero> availableHeroes, Consumer<Integer> onHeroSelected) {
		setBackground(new Color(222, 184, 135));
		int size = availableHeroes.size();
		setSize(BUTTON_WIDTH * size + HORIZONTAL_GAP * (size + 1), BUTTON_HEIGHT + 2 * VERTICAL_GAP);
		setLayout(null);
		int x = HORIZONTAL_GAP;
		for (int i = 0; i < size; i++) {
			int index = i;
			Hero hero = availableHeroes.get(i);
			JButton heroButton = new JButton(hero.getName());
			heroButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
			heroButton.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
			heroButton.setLocation(x, VERTICAL_GAP);
			heroButton.addActionListener(e -> onHeroSelected.accept(index));
			add(heroButton);
			x += BUTTON_WIDTH + HORIZONTAL_GAP;
		}
	}
}
