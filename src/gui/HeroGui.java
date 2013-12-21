package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import core.Hero;


public class HeroGui extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5926706015812873971L;
	private Hero hero;
	public static final int WIDTH = CardRackGui.HEIGHT;
	public static final int HEIGHT = WIDTH;
	public HeroGui()
	{
		hero = null;
		setSize(WIDTH,HEIGHT);
		setLocation(PanelGui.WIDTH-HealthGui.WIDTH-WIDTH,PanelGui.HEIGHT-HEIGHT);
	}
	
	public void setHero(Hero hero)
	{
		this.hero = hero;
	}
	
	@Override
	public void paint(Graphics g)
	{
		g.drawRect(0, 0, WIDTH, HEIGHT);
		if(hero == null)
		{
			return;
		}
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
		g.setColor(Color.BLACK);
		g.drawString(hero.getName(), WIDTH/2, HEIGHT/2);
	}
}
