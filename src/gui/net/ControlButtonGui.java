package gui.net;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import core.Constants;

public class ControlButtonGui extends JButton
{
	private static final long serialVersionUID = -2494394522921888799L;

	private static final int WIDTH = Constants.SCREEN_WIDTH / 12;
	private static final int HEIGHT = Constants.SCREEN_HEIGHT / 12;
	public ControlButtonGui(String text, ActionListener listener)
	{
		super(text);
		setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setHorizontalAlignment(JButton.CENTER);
		addActionListener(listener);
	}
}
