package ui.game;

import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import cards.Card;
import core.client.GamePanel;
import core.heroes.Hero;
import core.player.PlayerCardZone;
import listeners.game.CardOnHandListener;
import ui.game.interfaces.CardRackUI;
import ui.game.interfaces.CardUI;

/**
 * card rack (cards on hand) gui
 * 
 * @author Harry
 *
 */
public class CardRackGui extends JPanel implements CardOnHandListener, CardRackUI {
	private static final long serialVersionUID = -7373800552773539354L;
	public static final int WIDTH = GamePanelGui.WIDTH - EquipmentRackGui.WIDTH - HeroGui.WIDTH - LifebarGui.WIDTH;

	public static final int HEIGHT = 200;
	private final ActionListener listener;
	private List<CardGui> cards;

	public CardRackGui(GamePanel<? extends Hero> panel, PlayerCardZone zone) {
		setLayout(null);
		setSize(WIDTH, HEIGHT);
		setLocation(EquipmentRackGui.WIDTH, GamePanelGui.HEIGHT - HEIGHT);
		cards = new ArrayList<CardGui>();
		switch(zone) {
			case HAND:
				this.listener = e -> panel.getCurrentOperation().onCardClicked((CardGui) e.getSource());
				break;
			case EQUIPMENT:
				this.listener = e -> panel.getCurrentOperation().onEquipmentClicked((CardGui) e.getSource());
				break;
			case DELAYED:
				this.listener = e -> panel.getCurrentOperation().onDelayedClicked((CardGui) e.getSource());
				break;
			default:
				this.listener = null;
				break;
		}
	}
	
	public CardRackGui(GamePanel<? extends Hero> panel) {
		this(panel, PlayerCardZone.HAND);
	}
	
	@Override
	public void onCardAdded(Card card) {
		addCardGui(new CardGui(card), false);
	}

	@Override
	public synchronized void onCardRemoved(Card card) {
		for (int i = 0; i < cards.size(); i++) {
			if (cards.get(i).getCard().equals(card)) {
				remove(cards.remove(i));
				resetLocations();
				repaint();
				break;
			}
		}
	}

	public synchronized void addCardGui(CardGui cardGui, boolean enabled) {
		cardGui.setEnabled(enabled);
		cardGui.addActionListener(listener);
		cards.add(cardGui);
		add(cardGui, 0);
		resetLocations();
		repaint();
	}

	@Override
	public void paintComponents(Graphics g) {
		super.paintComponents(g);
		for (CardGui c : cards)
			c.paintComponents(g);
	}

	/**
	 * subtract offset from card's alpha values
	 * 
	 * @param offset
	 *            : amount of change, from 0 to 1
	 */
	protected void fadeCards(float offset) {
		for (CardGui c : cards)
			c.fade(offset);
	}

	protected synchronized void clearRack() {
		removeAll();
		cards.clear();
		repaint();
	}

	public synchronized void setCardSelected(Card card, boolean selected) {
		for (CardGui c : cards) {
			if (c.getCard().equals(card)) {
				if (selected) {
					c.select();
				} else {
					c.unselect();
				}
				return;
			}
		}
	}

	public synchronized void setCardSelectable(Card card, boolean selectable) {
		for (CardGui c : cards) {
			if (c.getCard().equals(card)) {
				c.setEnabled(selectable);
				repaint();
				return;
			}
		}
	}

	private void resetLocations() {
		int totalLength = cards.size() * CardGui.WIDTH;
		int stepLength;
		if (totalLength <= getWidth())
			stepLength = CardGui.WIDTH;
		else
			stepLength = (getWidth() - CardGui.WIDTH) / (cards.size() - 1);
		int size = cards.size();
		for (int i = 0; i < size; i++) {
			cards.get(i).setLocation(i * stepLength, 0);
		}
	}

	@Override
	public List<? extends CardUI> getCardUIs() {
		return cards;
	}

}
