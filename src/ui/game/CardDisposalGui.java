package ui.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import listeners.game.CardDisposalListener;
import cards.Card;

/**
 * Gui of card disposal area, public to all players
 * @author Harry
 *
 */
public class CardDisposalGui extends JPanel implements CardDisposalListener, ActionListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4151655146720492963L;
	public static final int WIDTH = 600;
	public static final int HEIGHT = CardGui.HEIGHT*2;
	private static final int TIME = 50;
	private CardRackGui disposal;
	private CardRackGui usage;
	private Timer timer;
	private int ms;
	private JPanel parent;
	
	public CardDisposalGui(JPanel parentPanel)
	{
		this.parent = parentPanel;
		setLayout(null);
		setSize(WIDTH,HEIGHT);
		setLocation((GamePanelUI.WIDTH-WIDTH)/2,(GamePanelUI.HEIGHT-HEIGHT)/2);
		usage = new CardRackGui(null);
		usage.setSize(WIDTH, CardGui.HEIGHT);
		usage.setLocation(0,0);
		add(usage);
		
		disposal = new CardRackGui(null);
		disposal.setSize(WIDTH, CardGui.HEIGHT);
		disposal.setLocation(0, CardGui.HEIGHT);
		add(disposal);
		timer = new Timer(10,this);
		ms = 0;
	}
	@Override
	public void onCardUsed(Card card) 
	{
		usage.onCardAdded(card);
		parent.validate();
		parent.repaint();
	}

	@Override
	public void onCardDisposed(Card card) 
	{
		disposal.onCardAdded(card);
		parent.validate();
		parent.repaint();
	}
	@Override
	public void refresh()
	{
		timer.start();
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(ms == 2*TIME)
		{
			usage.clearRack();
			disposal.clearRack();
			timer.stop();
			ms = 0;
		}
		else
		{
			ms++;
			if(ms > TIME)
			{
				usage.fadeCards(1.0f/TIME);
				disposal.fadeCards(1.0f/TIME);
			}
		}
	}
}
