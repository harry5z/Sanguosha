package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import core.Hero;


public class HeroGui extends JButton
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5926706015812873971L;
	private Hero hero;
	public static final int WIDTH = CardRackGui.HEIGHT;
	public static final int HEIGHT = WIDTH;
	public HeroGui(ActionListener listener)
	{
		hero = null;
		setEnabled(false);
		setSize(WIDTH,HEIGHT);
		setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
		setLocation(PanelGui.WIDTH-LifebarGui.WIDTH-WIDTH,PanelGui.HEIGHT-HEIGHT);
		addActionListener(listener);
	}
	
	public void setHero(Hero hero)
	{
		this.hero = hero;
		setText(hero.getName());
		repaint();
	}
	
}
