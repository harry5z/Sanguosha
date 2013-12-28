package gui;
import heroes.Blank;

import java.awt.Dimension;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.*;

import update.*;
import net.Client;
import listener.GameListener;
import core.Card;
import core.Hero;
import core.Master;
import core.Player;
import core.PlayerImpl;
import basics.*;


public class PanelGui extends JPanel implements ActionListener, GameListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5365518456813424707L;
	public static final int WIDTH = 1600;
	public static final int HEIGHT = 900;
	private static final int SELECTION_HEIGHT = 20;
	
	private final ExecutorService executor;
	private static final int POOL_SIZE = Runtime.getRuntime().availableProcessors();
	private CardRackGui cardRack;
	private EquipmentRackGui equipmentRack;
	private HeroGui heroGui;
	private LifebarGui healthGui;
	private CardDisposalGui disposalGui;
	
	private Player myself;
	private Client client;
	private ArrayList<PlayerGui> otherPlayers;
	private ButtonGui confirm;
	private ButtonGui cancel;
	private ButtonGui end;
	
	private JLabel deckSize;
	public PanelGui(int position)
	{
		setLayout(null);
		executor = Executors.newFixedThreadPool(POOL_SIZE);
		cardRack = new CardRackGui(this);
		equipmentRack = new EquipmentRackGui();
		heroGui = new HeroGui();
		healthGui = new LifebarGui();
		disposalGui = new CardDisposalGui();
		otherPlayers = new ArrayList<PlayerGui>();

		myself = new PlayerImpl("Player "+position,position);
		myself.setHero(new Blank());
		myself.registerGameListener(this);
		myself.registerCardOnHandListener(cardRack);
		myself.registerEquipmentListener(equipmentRack);
		myself.registerHealthListener(healthGui);
		myself.registerCardDisposalListener(disposalGui);
		heroGui.setHero(myself.getHero());
		
		confirm = new ButtonGui("Confirm",this);
		confirm.setLocation(0,HEIGHT-CardRackGui.HEIGHT-ButtonGui.HEIGHT);
		cancel = new ButtonGui("Cancel",this);
		cancel.setLocation(ButtonGui.WIDTH,HEIGHT-CardRackGui.HEIGHT-ButtonGui.HEIGHT);
		end = new ButtonGui("End",this);
		end.setLocation(ButtonGui.WIDTH*2,HEIGHT-CardRackGui.HEIGHT-ButtonGui.HEIGHT);
		
		
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		add(cardRack);
		add(equipmentRack);
		add(heroGui);
		add(healthGui);
		add(disposalGui);
		add(confirm);
		add(cancel);
		add(end);
		connect();
	}
	private void connect()
	{
		client = new Client(this);
		client.setHost("localhost");
		client.setPort(Master.DEFAULT_PORT);
		executor.execute(client);
	}
	@Override
	public void onPlayerAdded(Player player)
	{
		player.registerCardDisposalListener(disposalGui);
		PlayerGui p = new PlayerGui(player);
		otherPlayers.add(p);
		p.setLocation(WIDTH-(otherPlayers.size())*PlayerGui.WIDTH,0);
		add(p);
		repaint();
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object obj = e.getSource();
		if(obj instanceof CardGui)
		{
			Card card = ((CardGui)obj).getCard();
			myself.activateCardOnHand(card);
//			if(!myself.isSelected(card))
//				myself.selectCardOnHand(card);
//			else
//				myself.unselectCardOnHand(card);
		}
		else if(obj instanceof PlayerGui)
		{
			Player player = ((PlayerGui)obj).getPlayer();
			myself.selectTarget(player);
//			if(!myself.isSelected(player))
//				myself.selectTarget(player);
//			else
//				myself.unselectTarget(player);
		}
		else if(obj == confirm)
		{
			myself.confirm();
		}
		else if(obj == cancel)
		{
			myself.cancel();
		}
		else if(obj == end)
		{
			for(PlayerGui p : otherPlayers)
				p.setEnabled(false);
			setAllCardsEnabled(false);
			confirm.setEnabled(false);
			end.setEnabled(false);
			cancel.setEnabled(false);
		}
	}
//	private void processCard(Card card)
//	{
//		if(card.getType() == Card.BASIC)
//		{
//			String name = card.getName();
//			if(name.equals(Attack.ATTACK))
//			{
//				for(PlayerGui potentialTarget : otherPlayers)
//					if(myself.isPlayerInRange(potentialTarget.getPlayer()))
//							potentialTarget.setEnabled(true);
//				updateToSend = new RequireUseOfCardsByName(myself,new Dodge(),1);
//				cancel.setEnabled(true);
//			}
//			else if(name.equals(Peach.PEACH) || name.equals(Dodge.DODGE));
//			{
//				updateToSend = new UseOfCards(myself,card);
//				confirm.setEnabled(true);
//				cancel.setEnabled(true);
//			}
//		}
//	}
	private void setAllCardsEnabled(boolean b)
	{
		for(CardGui card : cardRack.getCardsOnHand())
			card.setEnabled(b);
	}
	private void setAllCardsEnabledExceptDodge()
	{
		for(CardGui card : cardRack.getCardsOnHand())
			if(!card.getName().equals(Dodge.DODGE))
				card.setEnabled(true);
	}

	@Override
	public void onNotified(Update update) 
	{
		update.playerOperation(myself);
	}
	@Override
	public void onSendToMaster(Update update)
	{
		client.sendToMaster(update);
	}
	@Override
	public void onTurnDealStarted()
	{
		cancel.setEnabled(true);
		end.setEnabled(true);
		setAllCardsEnabledExceptDodge();
	}
	@Override
	public void onTurnDealEnded()
	{
		cancel.setEnabled(false);
		end.setEnabled(false);
		setAllCardsEnabled(false);
	}
	@Override
	public void onCardSelected(Card card)
	{
		for(CardGui c : cardRack.getCardsOnHand())
			if(c.getCard().equals(card))
			{
				c.setLocation(c.getX(),c.getY()-SELECTION_HEIGHT);
				return;
			}
	}
	@Override
	public void onCardUnselected(Card card)
	{
		for(CardGui c : cardRack.getCardsOnHand())
			if(c.getCard().equals(card))
			{
				c.setLocation(c.getX(),c.getY()+SELECTION_HEIGHT);
				return;
			}
	}
	@Override
	public void onTargetSelected(Player player)
	{
		for(PlayerGui p : otherPlayers)
			if(p.getPlayer().equals(player))
			{
				p.setLocation(p.getX(),p.getY()+SELECTION_HEIGHT);
				return;
			}
	}
	@Override
	public void onTargetUnselected(Player player)
	{
		for(PlayerGui p : otherPlayers)
			if(p.getPlayer().equals(player))
			{
				p.setLocation(p.getX(),p.getY()-SELECTION_HEIGHT);
				return;
			}
	}
	@Override
	public void onCardSetSelectable(Card card, boolean selectable)
	{
		for(CardGui c : cardRack.getCardsOnHand())
			if(c.getCard().equals(card))
			{
				c.setEnabled(selectable);
				return;
			}
	}
	@Override
	public void onTargetSetSelectable(Player player, boolean selectable)
	{
		for(PlayerGui p : otherPlayers)
			if(p.getPlayer().equals(player))
			{
				p.setEnabled(selectable);
				return;
			}
	}
	@Override
	public void onConfirmSetEnabled(boolean isEnabled)
	{
		confirm.setEnabled(isEnabled);
	}
	@Override
	public void onCancelSetEnabled(boolean isEnabled)
	{
		cancel.setEnabled(isEnabled);
	}
	public static void main(String[] args)
	{
		JFrame f = new JFrame();
		Scanner scan = new Scanner(System.in);
		int pos = scan.nextInt();
		f.add(new PanelGui(pos));
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		f.setVisible(true);
		f.setLocation(200,0);
	}
}
