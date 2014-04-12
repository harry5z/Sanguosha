package gui;
import heroes.Blank;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import cards.Card;
import player.PlayerClientComplete;
import player.PlayerClientSimple;
import listener.GameListener;
import core.PlayerInfo;

/**
 * Main Display gui, also monitors card/target selections, confirm/cancel/end selections, etc.
 * @author Harry
 *
 */
public class PanelGui extends JPanel implements GameListener,Runnable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5365518456813424707L;
	public static final int WIDTH = 1600;
	public static final int HEIGHT = 900;

	private CardRackGui cardRack;
	private EquipmentRackGui equipmentRack;
	private HeroGui heroGui;
	private LifebarGui healthGui;
	private CardDisposalGui disposalGui;
	private CardSelectionPane pane;
	private JPanel customizedPanel;
	
	private PlayerClientComplete myself;
	private ArrayList<PlayerGui> otherPlayers;
	private ButtonGui confirm;
	private ButtonGui cancel;
	private ButtonGui end;
	
	private JLabel deckSize;
	private MessageBoxGui messageBox;
	public PanelGui(PlayerClientComplete player)
	{
		setLayout(null);
		myself = player;
		
		cardRack = new CardRackGui(this);
		equipmentRack = new EquipmentRackGui(this);
		heroGui = new HeroGui(this);
		healthGui = new LifebarGui();
		disposalGui = new CardDisposalGui(this);
		otherPlayers = new ArrayList<PlayerGui>();
		deckSize = new JLabel();
		deckSize.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
		deckSize.setSize(100,100);
		deckSize.setLocation(WIDTH-100,PlayerGui.HEIGHT);
		messageBox = new MessageBoxGui();
		messageBox.setLocation(equipmentRack.getWidth(),HEIGHT - cardRack.getHeight() - MessageBoxGui.HEIGHT);
		pane = null;

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
		add(deckSize);
		add(messageBox);
	}
	@Override
	public void onPlayerAdded(PlayerClientSimple player)
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
		if(pane != null)
		{
			remove(pane);
			repaint();
		}
		Object obj = e.getSource();
		if(obj instanceof CardGui)
		{
			Card card = ((CardGui)obj).getCard();
			myself.chooseCardOnHand(card);
		}
		else if(obj instanceof PlayerGui)
		{
			PlayerClientSimple player = ((PlayerGui)obj).getPlayer();
			myself.choosePlayer(player);
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
	@Override
	public void setCardSelected(Card card, boolean selected)
	{
		cardRack.setCardSelected(card, selected);
	}
	@Override
	public void setTargetSelected(PlayerInfo player, boolean selected)
	{
		for(PlayerGui p : otherPlayers)
			if(p.getPlayer().matches(player))
			{
				if(selected)
				{
					p.select();
					heroGui.select();
				}
				else
				{
					p.unselect();
					heroGui.unselect();
				}
				return;
			}
		
	}
	@Override
	public void setCardSelectable(Card card, boolean selectable)
	{
		cardRack.setCardSelectable(card, selectable);
	}
	@Override
	public void setTargetSelectable(PlayerInfo player, boolean selectable)
	{
		if(myself.matches(player))
			heroGui.setEnabled(selectable);
		for(PlayerGui p : otherPlayers)
			if(p.getPlayer().matches(player))
			{
				p.setEnabled(selectable);
				return;
			}
	}
	@Override
	public void setConfirmEnabled(boolean isEnabled)
	{
		confirm.setEnabled(isEnabled);
	}
	@Override
	public void setCancelEnabled(boolean isEnabled)
	{
		cancel.setEnabled(isEnabled);
	}
	@Override
	public void setEndEnabled(boolean isEnabled)
	{
		end.setEnabled(isEnabled);
	}
	@Override
	public void updateDeckSize(int size)
	{
		deckSize.setText(""+size);
	}
	@Override
	public void setMessage(String message)
	{
		messageBox.setMessage(message);
	}
	@Override
	public void clearMessage()
	{
		messageBox.clearMessage();
	}
	@Override
	public void run()
	{
		JFrame f = new JFrame();
		f.add(this);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		f.setLocation(200,0);
		f.setResizable(false);
		f.setVisible(true);
	}
	@Override
	public void onDisplayCardSelectionPane(PlayerClientSimple player, boolean showHand, boolean showEquipments, boolean showDecisions) 
	{
		pane = new CardSelectionPane(player,showHand,showEquipments,showDecisions,this);
		pane.setLocation((WIDTH-pane.getWidth())/2,(HEIGHT-CardRackGui.HEIGHT-pane.getHeight())/2);
		add(pane);
		setComponentZOrder(pane,0); //foremost
		pane.validate();
		pane.repaint();
	}
	@Override
	public void onDisplayCustomizedSelectionPaneAtCenter(JPanel panel) 
	{
		this.onRemoveCustomizedSelectionPane();
		customizedPanel = panel;
		customizedPanel.setLocation((WIDTH-customizedPanel.getWidth())/2,(HEIGHT-CardRackGui.HEIGHT-customizedPanel.getHeight())/2);
		add(customizedPanel);
		setComponentZOrder(customizedPanel, 0); //foremost
		customizedPanel.validate();
		customizedPanel.repaint();
	}
	@Override
	public void onRemoveCustomizedSelectionPane() 
	{
		if(customizedPanel != null)
		{
			remove(customizedPanel);
			customizedPanel = null;
			validate();
			repaint();
		}
	}
}
