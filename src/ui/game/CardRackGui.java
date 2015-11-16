package ui.game;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import cards.Card;
import listeners.game.CardOnHandListener;
import net.client.GamePanel;

/**
 * card rack (cards on hand) gui
 * @author Harry
 *
 */
public class CardRackGui extends JPanel implements CardOnHandListener {
	private static final long serialVersionUID = -7373800552773539354L;
	public static final int WIDTH = GamePanelUI.WIDTH - EquipmentRackGui.WIDTH - HeroGui.WIDTH - LifebarGui.WIDTH;

	public static final int HEIGHT = 200;
	private final ActionListener listener;
	private List<CardGui> cards;

	public CardRackGui(GamePanel panel)
	{
		setLayout(null);
		setSize(WIDTH,HEIGHT);
		setLocation(EquipmentRackGui.WIDTH,GamePanelUI.HEIGHT-HEIGHT);
		cards = new ArrayList<CardGui>();
		this.listener = e -> panel.getCurrentOperation().onCardClicked((CardGui) e.getSource());
			
	}
	@Override
	public void onCardAdded(Card card)
	{
		addCardGui(new CardGui(card), false);
	}
	@Override
	public synchronized void onCardRemoved(Card card)
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
	protected synchronized void addCardGui(CardGui cardGui, boolean enabled)
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
	protected synchronized void clearRack()
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