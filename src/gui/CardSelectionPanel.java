package gui;

import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import player.PlayerClientSimple;
import cards.equipments.Equipment;

public abstract class CardSelectionPanel extends JPanel implements ActionListener
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
	public CardSelectionPanel(PlayerClientSimple player, boolean showHand, boolean showEquipments, boolean showDecisions)
	{
		int verticalLocation = 0;
		setLayout(null);
		if(showHand)
		{
			int amount = player.getCardsOnHandCount();
			if(amount != 0)
			{
				JLabel label = new LabelGui("Cards on Hand");
				CardRackGui hand = new CardRackGui(this);
				while(amount != 0)
				{
					hand.addCardGui(new CardGui());
					amount--;
				}
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
				CardRackGui equipments = new CardRackGui(this);
				for(Equipment e : player.getEquipments())
					equipments.onCardAdded(e);
				
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
			setSize(CardRackGui.WIDTH, HEIGHT);
			setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
			setHorizontalAlignment(JButton.CENTER);

		}
	}
}
