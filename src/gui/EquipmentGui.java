package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JButton;

import core.Equipment;

public class EquipmentGui extends JButton
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7493423607741338720L;
	public static final int WIDTH = EquipmentRackGui.WIDTH;
	public static final int HEIGHT = EquipmentRackGui.HEIGHT / 4;
	private Equipment equipment;
	public EquipmentGui(int verticalLocation)
	{
		setSize(WIDTH,HEIGHT);
		setLocation(0,verticalLocation);
		equipment = null;
	}
	/**
	 * Set an equipment
	 * @param equipment
	 */
	public void setEquipment(Equipment equipment)
	{
		this.equipment = equipment;
	}
	@Override
	public void paint(Graphics g)
	{
		if(equipment == null)
		{
			g.drawRect(0, 0, WIDTH, HEIGHT);
			return;
		}
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, HEIGHT));
		g.setColor(Color.BLACK);
		g.drawString(equipment.getName(), 0, HEIGHT);
	}
}
