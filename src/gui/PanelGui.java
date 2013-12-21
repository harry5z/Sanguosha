package gui;
import java.awt.Dimension;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import core.PlayerImpl;
import basics.*;


public class PanelGui extends JPanel implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5365518456813424707L;
	private CardRackGui cardRack;
	private EquipmentRackGui equipmentRack;
	private HeroGui heroGui;
	public static final int WIDTH = 1600;
	public static final int HEIGHT = 900;
	public PanelGui()
	{
		setLayout(null);
		cardRack = new CardRackGui();
		equipmentRack = new EquipmentRackGui();
		heroGui = new HeroGui();
		
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		add(cardRack);
		add(equipmentRack);
		add(heroGui);
	}
	public void actionPerformed(ActionEvent e) 
	{
		
	}
	public static void main(String[] args)
	{
		JFrame f = new JFrame();
		f.add(new PanelGui());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		f.setVisible(true);
		f.setLocation(200,0);
	}
}
