package gui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import core.Card;

public class CardRackGui extends JPanel implements MouseMotionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7373800552773539354L;
	public static final int WIDTH = PanelGui.WIDTH - EquipmentRackGui.WIDTH - HeroGui.WIDTH - HealthGui.WIDTH;
	public static final int HEIGHT = 200;
	
	private ArrayList<CardGui> cards;
	public CardRackGui()
	{
		setLayout(null);
		setSize(WIDTH,HEIGHT);
		setLocation(EquipmentRackGui.WIDTH,PanelGui.HEIGHT-HEIGHT);
		cards = new ArrayList<CardGui>();
	}
	
	public void addCard(Card card)
	{
		CardGui cardGui = new CardGui(card);
		cardGui.addMouseMotionListener(this);
		cards.add(cardGui);
		add(cardGui);
	}
	
	public void removeCard(Card card)
	{
		for(int i = 0;i < cards.size();i++)
		{
			if(cards.get(i).getCard() == card)
			{
				cards.remove(i);
				return;
			}
		}
	}
	@Override
	public void paint(Graphics g)
	{
		g.drawRect(0, 0, WIDTH, HEIGHT);
		int totalLength = cards.size() * CardGui.WIDTH;
		int stepLength;
		if(totalLength <= WIDTH)
			stepLength = CardGui.WIDTH;
		else
			stepLength = (WIDTH-CardGui.WIDTH)/(cards.size()-1);
		
		for(int i = 0;i < cards.size();i++)
			cards.get(i).setLocation(i*stepLength,0);
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
