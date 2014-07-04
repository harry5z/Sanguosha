package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import player.PlayerSimple;
import cards.equipments.Equipment;

public class CardSelectionPane extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1339809013970250865L;

	/**
	 * Display player's hand/equipments/decisionArea
	 * @param player
	 * @param showHand
	 * @param showEquipments
	 * @param showDecisions
	 */
	public CardSelectionPane(PlayerSimple player, boolean showHand, boolean showEquipments, boolean showDecisions, ActionListener listener)
	{
		int verticalLocation = 0;
		setLayout(null);
		if(showHand)
		{
			int amount = player.getHandCount();
			if(amount != 0)
			{
				JLabel label = new LabelGui("Cards on Hand");
				CardRackGui hand = new CardRackGui(listener);
				for(int i = 0; i < amount;i++)
					hand.addCardGui(new CardGui(),true);

				label.setLocation(0,verticalLocation);
				add(label);
				verticalLocation += LabelGui.HEIGHT;
				hand.setLocation(0,verticalLocation);
				add(hand);
				verticalLocation += CardRackGui.HEIGHT;
			}
		}
		if(showEquipments)
		{
			if(player.isEquipped())
			{
				JLabel label = new LabelGui("Equipments");
				CardRackGui equipments = new CardRackGui(listener);
				for(Equipment e : player.getEquipments())
					equipments.addCardGui(new CardGui(e), true);
				
				label.setLocation(0,verticalLocation);
				add(label);
				verticalLocation += LabelGui.HEIGHT;
				
				equipments.setLocation(0,verticalLocation);
				add(equipments);
				verticalLocation += CardRackGui.HEIGHT;
			}
		}
//		if(showDecisions) // not yet implemented
//			size++;
		setSize(CardRackGui.WIDTH, verticalLocation);
		repaint();
	}
	
	private class LabelGui extends JLabel
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = -3890008522268599582L;
		public static final int HEIGHT = 30;
		public LabelGui(String text)
		{
			super(text);
			setBackground(Color.RED);
			setSize(CardRackGui.WIDTH, HEIGHT);
			setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
			setHorizontalAlignment(JButton.CENTER); //text in the middle

		}
	}
}
