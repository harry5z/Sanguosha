package ui.game.interfaces;

import java.util.List;

import javax.swing.JPanel;

import core.player.PlayerInfo;

/**
 * 
 * A representation of the client-side Game Interface. Command and Operation will
 * use this interface to update the UI for the player
 * 
 * @author Harry
 *
 */
public interface GameUI {

	/**
	 * invoked to enable/disable confirm button
	 * @param isEnabled
	 */
	public void setConfirmEnabled(boolean isEnabled);
	
	/**
	 * invoked to enable/disable cancel button
	 * @param isEnabled
	 */
	public void setCancelEnabled(boolean isEnabled);
	
	/**
	 * invoked to enable/disable end button. This method should only be
	 * called in DealOperation
	 * @param isEnabled
	 */
	public void setEndEnabled(boolean isEnabled);
	
	public PlayerUI getOtherPlayerUI(String name);
	
	public PlayerUI getOtherPlayerUI(PlayerInfo other);

	public HeroUI getHeroUI();

	public CardRackUI getCardRackUI();
	
	public EquipmentRackUI getEquipmentRackUI();

	public List<PlayerUI> getOtherPlayersUI();
	
	public void displayCustomizedSelectionPaneAtCenter(JPanel panel);
	
	public JPanel getSelectionPane();
	
	public void removeSelectionPane();
	
	public void setMessage(String message);
	
	public void clearMessage();

}
