package ui.game;

import java.awt.Font;

import javax.swing.JButton;

import cards.equipments.Equipment;

public class EquipmentGui extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7493423607741338720L;
	public static final int WIDTH = EquipmentRackGui.WIDTH;
	public static final int HEIGHT = EquipmentRackGui.HEIGHT / 4;
	private Equipment equipment;

	public EquipmentGui(int verticalLocation) {
		setSize(WIDTH, HEIGHT);
		setLocation(0, verticalLocation);
		setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
		this.setHorizontalAlignment(JButton.CENTER);
		setEnabled(false);
		equipment = null;
	}

	/**
	 * Set an equipment
	 * 
	 * @param equipment
	 */
	public synchronized void setEquipment(Equipment equipment) {
		this.equipment = equipment;
		if (equipment == null)
			setText("");
		else
			this.setText(equipment.getName());
		// repaint();
	}

	public synchronized Equipment getEquipment() {
		return equipment;
	}
	// @Override
	// public void paint(Graphics g)
	// {
	// super.paint(g);
	// if(equipment == null)
	// {
	// g.drawRect(0, 0, WIDTH, HEIGHT);
	// return;
	// }
	// g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
	// g.setColor(Color.BLACK);
	//
	// g.drawString(equipment.getName(), 0,20);
	// }
}
