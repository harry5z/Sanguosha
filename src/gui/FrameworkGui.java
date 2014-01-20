package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import listener.FrameworkListener;
import core.Framework;

public class FrameworkGui extends JButton implements Runnable,FrameworkListener,ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3644826715427168652L;
	private Framework framework;
	private JFrame frame;
	public FrameworkGui(Framework f)
	{
		framework = f;
	}
	@Override
	public void run() 
	{
		framework.registerFrameworkListener(this);
		setPreferredSize(new Dimension(100,100));
		setText("Start");
		addActionListener(this);
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.pack();
		frame.setVisible(true);
		frame.setLocation(0,0);
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == this)
		{
			setEnabled(false);
			framework.start();
			frame.setVisible(false);
		}
	}
}
