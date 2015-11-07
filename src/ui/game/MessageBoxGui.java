package ui.game;

import java.awt.Font;
import javax.swing.JLabel;

public class MessageBoxGui extends JLabel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1741141955211814971L;
	public static final int WIDTH = CardRackGui.WIDTH;
	public static final int HEIGHT = 40;
	
	public MessageBoxGui()
	{
		setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
		setHorizontalAlignment(JLabel.CENTER);
		setSize(WIDTH,HEIGHT);
	}
	
	public void setMessage(String message)
	{
		setText(message);
	}
	public void clearMessage()
	{
		setText("");
	}
}
