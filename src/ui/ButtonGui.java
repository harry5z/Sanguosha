package ui;

import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ButtonGui extends JButton
{
	private static final long serialVersionUID = 772977252526814797L;
	public static final int WIDTH = 100;
	public static final int HEIGHT = 50;
	public ButtonGui(String text,ActionListener listener)
	{
		setText(text);
		setEnabled(false);
		setSize(WIDTH,HEIGHT);
		setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
		setHorizontalAlignment(JButton.CENTER);
		addActionListener(listener);
	}
}
