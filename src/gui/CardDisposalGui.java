package gui;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import cards.Card;
import listener.CardDisposalListener;

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

	private CardRackGui disposal;
	private CardRackGui usage;
	private Timer timer;
	private int ms;
	
	public CardDisposalGui()
	{
		setLayout(null);
		setSize(WIDTH,HEIGHT);
		setLocation((PanelGui.WIDTH-WIDTH)/2,(PanelGui.HEIGHT-HEIGHT)/2);
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
	}

	@Override
	public void onCardDisposed(Card card) 
	{
		disposal.onCardAdded(card);
	}
	@Override
	public void refresh()
	{
		timer.start();
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(ms == 50)
		{
			usage.clearRack();
			disposal.clearRack();
			timer.stop();
			ms = 0;
		}
		else
			ms++;
	}
}
