package gui;
import heroes.Blank;

import java.awt.Dimension;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import player.PlayerOriginalClientComplete;
import player.PlayerOriginalClientSimple;
import listener.GameListener;
import core.Card;
import core.PlayerInfo;

/**
 * Main Display gui, also monitors card/target selections, confirm/cancel/end selections, etc.
 * @author Harry
 *
 */
public class PanelGui extends JPanel implements ActionListener, GameListener,Runnable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5365518456813424707L;
	public static final int WIDTH = 1600;
	public static final int HEIGHT = 900;
	private static final int SELECTION_HEIGHT = 20;
	

	private CardRackGui cardRack;
	private EquipmentRackGui equipmentRack;
	private HeroGui heroGui;
	private LifebarGui healthGui;
	private CardDisposalGui disposalGui;
	
	private PlayerOriginalClientComplete myself;
	private ArrayList<PlayerGui> otherPlayers;
	private ButtonGui confirm;
	private ButtonGui cancel;
	private ButtonGui end;
	
	private JLabel deckSize;
	public PanelGui(PlayerOriginalClientComplete player)
	{
		setLayout(null);
		myself = player;
		
		cardRack = new CardRackGui(this);
		equipmentRack = new EquipmentRackGui();
		heroGui = new HeroGui(this);
		healthGui = new LifebarGui();
		disposalGui = new CardDisposalGui();
		otherPlayers = new ArrayList<PlayerGui>();

		myself.setHero(new Blank());//change in the future
		heroGui.setHero(myself.getHero());
		
		myself.registerGameListener(this);
		myself.registerCardOnHandListener(cardRack);
		myself.registerEquipmentListener(equipmentRack);
		myself.registerHealthListener(healthGui);
		myself.registerCardDisposalListener(disposalGui);
		
		
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
	}
	@Override
	public void onPlayerAdded(PlayerOriginalClientSimple player)
	{
		player.registerCardDisposalListener(disposalGui);
		PlayerGui p = new PlayerGui(player,this);
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
			myself.chooseCardOnHand(card);
//			if(!myself.isSelected(card))
//				myself.selectCardOnHand(card);
//			else
//				myself.unselectCardOnHand(card);
		}
		else if(obj instanceof PlayerGui)
		{
			PlayerOriginalClientSimple player = ((PlayerGui)obj).getPlayer();
			myself.choosePlayer(player);
//			if(!myself.isSelected(player))
//				myself.selectTarget(player);
//			else
//				myself.unselectTarget(player);
		}
		else if(obj instanceof HeroGui)
		{
			myself.choosePlayer(myself);
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
			myself.endDealing();
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

	@Override
	public void onCardSelected(Card card)
	{
		cardRack.selectCard(card);
	}
	@Override
	public void onCardUnselected(Card card)
	{
		cardRack.unselectCard(card);
	}
	@Override
	public void onTargetSelected(PlayerInfo player)
	{
		for(PlayerGui p : otherPlayers)
			if(p.getPlayer().isEqualTo(player))
			{
				p.setLocation(p.getX(),p.getY()+SELECTION_HEIGHT);
				return;
			}
	}
	@Override
	public void onTargetUnselected(PlayerInfo player)
	{
		for(PlayerGui p : otherPlayers)
			if(p.getPlayer().isEqualTo(player))
			{
				p.setLocation(p.getX(),p.getY()-SELECTION_HEIGHT);
				return;
			}
	}
	@Override
	public void onCardSetSelectable(Card card, boolean selectable)
	{
		cardRack.setCardSelectable(card, selectable);
	}
	@Override
	public void onTargetSetSelectable(PlayerInfo player, boolean selectable)
	{
		if(myself.isEqualTo(player))
			heroGui.setEnabled(selectable);
		for(PlayerGui p : otherPlayers)
			if(p.getPlayer().isEqualTo(player))
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
	@Override
	public void onEndSetEnabled(boolean isEnabled)
	{
		end.setEnabled(isEnabled);
	}
	public void run()
	{
		JFrame f = new JFrame();
		f.add(this);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		f.setLocation(200,0);
		f.setVisible(true);
	}
}
