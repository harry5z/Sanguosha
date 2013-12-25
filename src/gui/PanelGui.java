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
	private Player myself;
	private Client client;
	private ArrayList<PlayerGui> otherPlayers;
	private ButtonGui confirm;
	private ButtonGui cancel;
	private ButtonGui end;
	
	private CardGui cardSelected;
	private PlayerGui targetSelected;
	private Update updateToSend;
	private JLabel deckSize;
	public PanelGui(int position)
	{
		setLayout(null);
		executor = Executors.newFixedThreadPool(POOL_SIZE);
		cardRack = new CardRackGui(this);
		equipmentRack = new EquipmentRackGui();
		heroGui = new HeroGui();
		healthGui = new LifebarGui();
		otherPlayers = new ArrayList<PlayerGui>();

		myself = new PlayerImpl("Player "+position,position);
		myself.setHero(new Blank());
		myself.registerGameListener(this);
		myself.registerCardOnHandListener(cardRack);
		myself.registerEquipmentListener(equipmentRack);
		myself.registerHealthListener(healthGui);
		heroGui.setHero(myself.getHero());
		
		confirm = new ButtonGui("Confirm",this);
		confirm.setLocation(0,HEIGHT-CardRackGui.HEIGHT-ButtonGui.HEIGHT);
		cancel = new ButtonGui("Cancel",this);
		cancel.setLocation(ButtonGui.WIDTH,HEIGHT-CardRackGui.HEIGHT-ButtonGui.HEIGHT);
		end = new ButtonGui("End",this);
		end.setLocation(ButtonGui.WIDTH*2,HEIGHT-CardRackGui.HEIGHT-ButtonGui.HEIGHT);
		
		cardSelected = null;
		targetSelected = null;
		updateToSend = null;
		
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		add(cardRack);
		add(equipmentRack);
		add(heroGui);
		add(healthGui);
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
			if(cardSelected != null)
				cardSelected.setLocation(cardSelected.getX(),cardSelected.getY()+SELECTION_HEIGHT);
			
			cardSelected = (CardGui)obj;
			cardSelected.setLocation(cardSelected.getX(),cardSelected.getY()-SELECTION_HEIGHT);
			processCard(cardSelected.getCard());
		}
		else if(obj instanceof PlayerGui)
		{
			if(targetSelected != null)
				targetSelected.setLocation(targetSelected.getX(),targetSelected.getY()-SELECTION_HEIGHT);
			
			targetSelected = (PlayerGui)obj;
			targetSelected.setLocation(targetSelected.getX(),targetSelected.getY()+SELECTION_HEIGHT);
			((RequireUseOfCardsByName)updateToSend).setTarget(targetSelected.getPlayer());
			confirm.setEnabled(true);
		}
		else if(obj == confirm)
		{
			client.sendToMaster(updateToSend);
			reset();
			setAllCardsEnabled(false);
		}
		else if(obj == cancel)
		{
			reset();
		}
		else if(obj == end)
		{
			reset();
			end.setEnabled(false);
			cancel.setEnabled(false);
			client.sendToMaster(new NextStage(myself.getCurrentStage()));
		}
	}
	private void processCard(Card card)
	{
		if(card.getType() == Card.BASIC)
		{
			String name = card.getName();
			if(name.equals(Attack.ATTACK))
			{
				for(PlayerGui potentialTarget : otherPlayers)
					if(myself.isPlayerInRange(potentialTarget.getPlayer()))
							potentialTarget.setEnabled(true);
				updateToSend = new RequireUseOfCardsByName(myself,new Dodge(),1);
				cancel.setEnabled(true);
			}
			else if(name.equals(Peach.PEACH) || name.equals(Dodge.DODGE));
			{
				updateToSend = new UseOfCards(myself,card);
				confirm.setEnabled(true);
				cancel.setEnabled(true);
			}
		}
	}
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
	/**
	 * <li>No card selected
	 * <li>No target selected
	 * <li>No playerGui enabled(targetSelection off)
	 * <li>No update to send
	 * <li>confirm disabled
	 */
	private void reset()
	{
		if(cardSelected != null)
		{
			cardSelected.setLocation(cardSelected.getX(),cardSelected.getY()+SELECTION_HEIGHT);
			cardSelected = null;
		}
		if(targetSelected != null)
		{
			targetSelected.setLocation(targetSelected.getX(),targetSelected.getY()-SELECTION_HEIGHT);
			targetSelected = null;
		}
		for(PlayerGui p : otherPlayers)
			p.setEnabled(false);
		confirm.setEnabled(false);
		updateToSend = null;
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
				c.setLocation(cardSelected.getX(),cardSelected.getY()-SELECTION_HEIGHT);
	}
	@Override
	public void onCardUnselected(Card card)
	{
		for(CardGui c : cardRack.getCardsOnHand())
			if(c.getCard().equals(card))
				c.setLocation(cardSelected.getX(),cardSelected.getY()+SELECTION_HEIGHT);
	}
	@Override
	public void onPlayerSelected(Player player)
	{
		for(PlayerGui p : otherPlayers)
			if(p.getPlayer().equals(player))
				p.setLocation(targetSelected.getX(),targetSelected.getY()+SELECTION_HEIGHT);
	}
	@Override
	public void onPlayerUnselected(Player player)
	{
		for(PlayerGui p : otherPlayers)
			if(p.getPlayer().equals(player))
				p.setLocation(targetSelected.getX(),targetSelected.getY()-SELECTION_HEIGHT);
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
