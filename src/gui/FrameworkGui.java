package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import listener.FrameworkListener;
import core.Framework4Gui;

public class FrameworkGui extends JFrame implements Runnable,FrameworkListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3644826715427168652L;
	private final Framework4Gui framework;
	private JButton startButton;
	public FrameworkGui(Framework4Gui f)
	{
		framework = f;
		framework.registerFrameworkListener(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(0,0);
	}
	@Override
	public void run() 
	{
		setLayout(new BorderLayout());
		startButton = new JButton("Start");
		startButton.setPreferredSize(new Dimension(100,100));
		startButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				framework.start();
			}
		});
		JButton resetButton = new JButton("Reset");
		resetButton.setPreferredSize(new Dimension(100,100));
		resetButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				framework.reset();
			}
		});
		add(startButton, BorderLayout.NORTH);
		add(resetButton, BorderLayout.SOUTH);
		pack();
		setVisible(true);
	}
	@Override
	public void onGameStarted() 
	{
		startButton.setEnabled(false);
	}
	@Override
	public void onGameReset()
	{
		startButton.setEnabled(true);
	}
}
