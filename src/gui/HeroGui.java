package gui;

import heroes.Hero;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import core.Constants;


public class HeroGui extends JButton
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5926706015812873971L;
	private Hero hero;
	public static final int WIDTH = CardRackGui.HEIGHT;
	public static final int HEIGHT = WIDTH;
	private boolean selected = false;
	public HeroGui(ActionListener listener)
	{
		hero = null;
		setEnabled(false);
		setSize(WIDTH,HEIGHT);
		setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
		setLocation(PanelGui.WIDTH-LifebarGui.WIDTH-WIDTH,PanelGui.HEIGHT-HEIGHT);
		addActionListener(listener);
	}
	protected void select()
	{
		selected = true;
		repaint();
	}
	protected void unselect()
	{
		selected = false;
		repaint();
	}
	public void setHero(Hero hero)
	{
		this.hero = hero;
		setText(hero.getName());
		repaint();
	}
	
	public Hero getHero()
	{
		return hero;
	}
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		if(selected)
			g.setColor(Color.RED);
		else
			g.setColor(Color.BLACK);
		((Graphics2D) g).setStroke(new BasicStroke(Constants.BORDER_WIDTH));
		g.drawRect(0, 0, WIDTH, HEIGHT);	
	}
}
