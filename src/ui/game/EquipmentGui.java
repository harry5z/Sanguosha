package ui.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;

import cards.equipments.Equipment;
import core.Constants;
import ui.game.interfaces.EquipmentUI;

public class EquipmentGui extends JButton implements EquipmentUI {

	private static final long serialVersionUID = -7493423607741338720L;
	
	public static final int WIDTH = EquipmentRackGui.WIDTH;
	public static final int HEIGHT = EquipmentRackGui.HEIGHT / 4;
	
	private Equipment equipment;
	private boolean activated;

	public EquipmentGui(int verticalLocation) {
		setSize(WIDTH, HEIGHT);
		setLocation(0, verticalLocation);
		setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
		this.setHorizontalAlignment(JButton.CENTER);
		setEnabled(false);
		this.activated = false;
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

	@Override
	public synchronized Equipment getEquipment() {
		return equipment;
	}
	
	@Override
	public void paint(Graphics g) {
	  super.paint(g);
	  if (this.activated) {
		  Graphics2D graphics = (Graphics2D) g;
		  graphics.setColor(Color.RED);
		  graphics.setStroke(new BasicStroke(Constants.BORDER_WIDTH));
		  graphics.drawRect(0, 0, WIDTH, HEIGHT);
	  }
	}

	@Override
	public synchronized void setActivated(boolean activated) {
		this.activated = activated;
		this.validate();
		this.repaint();
	}

	@Override
	public synchronized void setActivatable(boolean activatable) {
		this.setEnabled(activatable);
	}
}
