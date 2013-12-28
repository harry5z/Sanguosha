package gui;

import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import listener.CardOnHandListener;
import core.Card;

public class CardRackGui extends JPanel implements MouseMotionListener, CardOnHandListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7373800552773539354L;
	public static final int WIDTH = PanelGui.WIDTH - EquipmentRackGui.WIDTH - HeroGui.WIDTH - LifebarGui.WIDTH;
	public static final int HEIGHT = 200;
	private ActionListener listener;
	private ArrayList<CardGui> cards;

	public CardRackGui(ActionListener listener)
	{
		setLayout(null);
		setSize(WIDTH,HEIGHT);
		setLocation(EquipmentRackGui.WIDTH,PanelGui.HEIGHT-HEIGHT);
		cards = new ArrayList<CardGui>();
		this.listener = listener;
	}
	@Override
	public void onCardAdded(Card card)
	{
		CardGui cardGui = new CardGui(card);
		cardGui.addMouseMotionListener(this);
		cardGui.addActionListener(listener);
		cardGui.setEnabled(false);
		cards.add(cardGui);
		add(cardGui);
		repaint();
	}
	@Override
	public void onCardRemoved(Card card)
	{
		for(int i = 0;i < cards.size();i++)
		{
			if(cards.get(i).getCard() == card)
			{
				cards.remove(i);
				repaint();
				return;
			}
		}
	}
	protected void clearRack()
	{
		cards.clear();
		repaint();
	}
	public ArrayList<CardGui> getCardsOnHand()
	{
		return cards;
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
