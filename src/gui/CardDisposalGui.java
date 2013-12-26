package gui;

import javax.swing.JPanel;

import core.Card;
import listener.CardDisposalListener;

public class CardDisposalGui extends JPanel implements CardDisposalListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4151655146720492963L;
	public static final int WIDTH = 600;
	public static final int HEIGHT = CardGui.HEIGHT*2;

	private CardRackGui disposal;
	private CardRackGui usage;
	
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
		usage.clearRack();
		disposal.clearRack();
	}
}
