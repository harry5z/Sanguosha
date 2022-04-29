package ui.game.custom;

import java.awt.Color;
import java.awt.Font;
import java.util.Collection;
import java.util.Queue;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cards.equipments.Equipment.EquipmentType;
import core.client.GamePanel;
import core.player.PlayerCardZone;
import core.player.PlayerSimple;
import ui.game.CardGui;
import ui.game.CardRackGui;
import utils.DelayedStackItem;

public class CardSelectionPane extends JPanel {
	private static final long serialVersionUID = -1L;

	public CardSelectionPane(
		PlayerSimple player,
		Collection<PlayerCardZone> zones,
		Collection<EquipmentType> equipmentTypes,
		GamePanel panel
	) {
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
				for (EquipmentType type : equipmentTypes) {
					if (player.isEquipped(type)) {
						equipments.addCardGui(new CardGui(player.getEquipment(type)), true);
					}
				}
				label.setLocation(0, verticalLocation);
				add(label);
				verticalLocation += LabelGui.HEIGHT;

				equipments.setLocation(0, verticalLocation);
				add(equipments);
				verticalLocation += CardRackGui.HEIGHT;
			}
		}
		if (zones.contains(PlayerCardZone.DELAYED)) {
			Queue<DelayedStackItem> delayedQueue = player.getDelayedQueue();
			if (!delayedQueue.isEmpty()) {
				JLabel label = new LabelGui("Delayed");
				CardRackGui delayed = new CardRackGui(panel, PlayerCardZone.DELAYED);
				for (DelayedStackItem item : delayedQueue) {
					delayed.addCardGui(new CardGui(item.delayed), true);
				}

				label.setLocation(0, verticalLocation);
				add(label);
				verticalLocation += LabelGui.HEIGHT;

				delayed.setLocation(0, verticalLocation);
				add(delayed);
				verticalLocation += CardRackGui.HEIGHT;
			}
		}
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
