package ui.game.custom;

import java.awt.Color;
import java.awt.Font;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cards.equipments.Equipment;
import core.client.GamePanel;
import core.heroes.Hero;
import core.player.PlayerCardZone;
import core.player.PlayerSimple;
import ui.game.CardGui;
import ui.game.CardRackGui;

public class CardSelectionPane extends JPanel {
	private static final long serialVersionUID = -1L;

	/**
	 * Display player's hand/equipments/decisionArea
	 * 
	 * @param player
	 * @param showHand
	 * @param showEquipments
	 * @param showDelayed
	 */
	public CardSelectionPane(PlayerSimple player, Collection<PlayerCardZone> zones, GamePanel<? extends Hero> panel) {
		int verticalLocation = 0;
		setLayout(null);
		if (zones.contains(PlayerCardZone.HAND)) {
			int amount = player.getHandCount();
			if (amount != 0) {
				JLabel label = new LabelGui("Cards on Hand");
				CardRackGui hand = new CardRackGui(panel, PlayerCardZone.HAND);
				for (int i = 0; i < amount; i++) {
					hand.addCardGui(new CardGui(), true);
				}

				label.setLocation(0, verticalLocation);
				add(label);
				verticalLocation += LabelGui.HEIGHT;
				hand.setLocation(0, verticalLocation);
				add(hand);
				verticalLocation += CardRackGui.HEIGHT;
			}
		}
		if (zones.contains(PlayerCardZone.EQUIPMENT)) {
			if (player.isEquipped()) {
				JLabel label = new LabelGui("Equipments");
				CardRackGui equipments = new CardRackGui(panel, PlayerCardZone.EQUIPMENT);
				for (Equipment e : player.getEquipments()) {
					equipments.addCardGui(new CardGui(e), true);
				}

				label.setLocation(0, verticalLocation);
				add(label);
				verticalLocation += LabelGui.HEIGHT;

				equipments.setLocation(0, verticalLocation);
				add(equipments);
				verticalLocation += CardRackGui.HEIGHT;
			}
		}
		// TODO: show Delayed zone
		setSize(CardRackGui.WIDTH, verticalLocation);
		repaint();
	}

	private static class LabelGui extends JLabel {
		private static final long serialVersionUID = -1L;
		public static final int HEIGHT = 30;

		public LabelGui(String text) {
			super(text);
			setBackground(Color.RED);
			setSize(CardRackGui.WIDTH, HEIGHT);
			setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
			setHorizontalAlignment(JButton.CENTER); // text in the middle
		}
	}
}
