package ui.game.interfaces;

import java.util.Collection;
import java.util.List;

import javax.swing.JPanel;

import core.GameState;
import core.client.ClientPanelUI;
import core.heroes.Hero;
import core.player.PlayerCardZone;
import core.player.PlayerInfo;
import core.player.PlayerSimple;

public interface ClientGameUI<T extends Hero> extends ClientPanelUI, GameState {

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
	 * invoked to enable/disable end button
	 * @param isEnabled
	 */
	public void setEndEnabled(boolean isEnabled);
	
	public PlayerUI getOtherPlayerUI(String name);
	
	public PlayerUI getOtherPlayerUI(PlayerInfo other);

	public HeroUI<T> getHeroUI();

	public CardRackUI getCardRackUI();

	public List<? extends PlayerUI> getOtherPlayersUI();
	
	/**
	 * Display the card selection pane on screen
	 * @param player : the owner of these cards
	 * @param showHand : whether to display player's cards on hand
	 * @param showEquipments : whether to display player's equipments
	 * @param showDecisions : whether to display player's decision area
	 */
	public void displayCardSelectionPane(PlayerSimple player, Collection<PlayerCardZone> zones);
	
	public void displayCustomizedSelectionPaneAtCenter(JPanel panel);
	
	public void removeSelectionPane();

	public void showCountdownBar();
	
}
