package ui;

import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import listeners.CardOnHandListener;
import cards.Card;

/**
 * card rack (cards on hand) gui
 * @author Harry
 *
 */
public class CardRackGui extends JPanel implements CardOnHandListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7373800552773539354L;
	public static final int WIDTH = PanelGui.WIDTH - EquipmentRackGui.WIDTH - HeroGui.WIDTH - LifebarGui.WIDTH;

	public static final int HEIGHT = 200;
	private ActionListener listener;
	private List<CardGui> cards;

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
		addCardGui(new CardGui(card), false);
	}
	@Override
	public void onCardRemoved(Card card)
	{
		for(int i = 0;i < cards.size();i++)
		{
			if(cards.get(i).getCard().equals(card))
			{
				remove(cards.remove(i));
				resetLocations();
				repaint();
				break;
			}
		}
	}
	protected void addCardGui(CardGui cardGui, boolean enabled)
	{
		cardGui.setEnabled(enabled);
		cardGui.addActionListener(listener);
		cards.add(cardGui);
		add(cardGui,0);
		resetLocations();
		repaint();
	}
	@Override
	public void paintComponents(Graphics g)
	{
		super.paintComponents(g);
		for(CardGui c : cards)
			c.paintComponents(g);
	}
	/**
	 * subtract offset from card's alpha values
	 * @param offset : amount of change, from 0 to 1
	 */
	protected void fadeCards(float offset)
	{
		for(CardGui c : cards)
			c.fade(offset);
	}
	protected void clearRack()
	{
		removeAll();
		cards.clear();
		repaint();
	}
	protected void setCardSelected(Card card, boolean selected)
	{
		for(CardGui c : cards)
		{
			if(c.getCard().equals(card))
			{
				if(selected)
					c.select();
				else
					c.unselect();
				return;
			}
		}
	}
	protected void setCardSelectable(Card card, boolean selectable)
	{
		for(CardGui c : cards)
			if(c.getCard().equals(card))
			{
				c.setEnabled(selectable);
				repaint();
				return;
			}
	}
	private void resetLocations()
	{
		int totalLength = cards.size() * CardGui.WIDTH;
		int stepLength;
		if(totalLength <= getWidth())
			stepLength = CardGui.WIDTH;
		else
			stepLength = (getWidth()-CardGui.WIDTH)/(cards.size()-1);
		int size = cards.size();
		for(int i = 0;i < size;i++)
		{
			cards.get(i).setLocation(i*stepLength,0);
		}
	}

}
