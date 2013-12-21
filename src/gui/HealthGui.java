package gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class HealthGui extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4065203819976238788L;
	public static final int WIDTH = 50;
	public static final int HEIGHT = CardRackGui.HEIGHT;
	private int limit;
	private int current;
	public HealthGui()
	{
		setSize(WIDTH,HEIGHT);
		setLocation(PanelGui.WIDTH-WIDTH,PanelGui.HEIGHT-HEIGHT);
		setLayout(null);
		limit = 0;
		current = 0;
	}
	
	public void setHealthLimit(int limit)
	{
		this.limit = limit;
	}
	public void setHealthCurrent(int current)
	{
		this.current = current;
	}
	public int getHealthLimit()
	{
		return limit;
	}
	public int getHealthCurrent()
	{
		return current;
	}
	public void changeHealthBy(int amount)
	{
		current += amount;
		if(current > limit)
			current = limit;
	}
	
	@Override
	public void paint(Graphics g)
	{
		g.drawRect(0, 0, WIDTH, HEIGHT);
		if(limit == 0)
			return;
		int diameter = HEIGHT / (limit*2+1);
		int x = (WIDTH - diameter) / 2;
		for(int i = 0;i < limit;i++)
		{
			if(i < current)
				g.setColor(Color.GREEN);
			else
				g.setColor(Color.RED);
				g.fillOval(x, (2*i+1)*diameter, diameter, diameter);
		}
	}
}
