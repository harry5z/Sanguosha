package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import listener.FrameworkListener;
import core.Framework;

public class FrameworkGui extends JButton implements Runnable,FrameworkListener,ActionListener
{
	private Framework framework;
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
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(this);
		f.pack();
		f.setVisible(true);
		f.setLocation(0,0);
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == this)
			framework.start();
	}
}
