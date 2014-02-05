package gui;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import cards.Card;
import listener.CardOnHandListener;

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
	private static final int SELECTION_HEIGHT = 20;

	public static final int WIDTH = PanelGui.WIDTH - EquipmentRackGui.WIDTH - HeroGui.WIDTH - LifebarGui.WIDTH;

	public static final int HEIGHT = 200;
	private ActionListener listener;
	private ArrayList<CardGui> cards;

	public CardRackGui(ActionListener listener)
	{
		setLayout(null);
		setSize(WIDTH,HEIGHT+SELECTION_HEIGHT);
		setLocation(EquipmentRackGui.WIDTH,PanelGui.HEIGHT-HEIGHT-SELECTION_HEIGHT);
		cards = new ArrayList<CardGui>();
		this.listener = listener;
	}
	@Override
	public void onCardAdded(Card card)
	{
		CardGui cardGui = new CardGui(card);
		cardGui.setRolloverEnabled(false);
		cardGui.addActionListener(listener);
		cardGui.setEnabled(false);
		cards.add(cardGui);
		add(cardGui,0);
		resetLocations();
		repaint();
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
	protected void clearRack()
	{
		removeAll();
		cards.clear();
		repaint();
	}
	protected void selectCard(Card card)
	{
		for(CardGui c : cards)
		{
			if(c.getCard().equals(card))
			{
				c.moveVerticallyTo(0);
				repaint();
				return;
			}
		}
	}
	protected void unselectCard(Card card)
	{
		for(CardGui c : cards)
			if(c.getCard().equals(card))
			{
				c.moveVerticallyTo(SELECTION_HEIGHT);
				repaint();
				return;
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
			cards.get(i).setLocation(i*stepLength,SELECTION_HEIGHT);
		}
	}

}
