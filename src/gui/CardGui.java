package gui;
import java.awt.*;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import core.Card;
import basics.*;

public class CardGui extends JPanel
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8973362684095284243L;
	public static final int WIDTH = 142;
	public static final int HEIGHT = 200;
	private Card c;
	private String number;
	private String suit;
	private Image img;
	public CardGui(Card card)
	{
		c = card;	
		setSize(WIDTH,HEIGHT);
		number = numToString(c.getNumber());
		suit(card.getSuit());
		readImage(c);
	}
	private void readImage(Card k)
	{
		try
		{
			img = ImageIO.read(getClass().getResource("cards/"+c.getName()+".png"));
		} 
		catch (IOException e) 
		{
			System.out.println("File not found");
		}
	}
	private void suit(int n)
	{
		switch(n)
		{
		case Card.SPADE:
			suit = "SPADE";
			break;
		case Card.HEART:
			suit = "HEART";
			break;
		case Card.CLUB:
			suit = "CLUB";
			break;
		case Card.DIAMOND:
			suit = "DIAMOND";
			break;
		}
		
	}
	private String numToString(int n)
	{
		if(n == 13)
			return "K";
		else if(n == 12)
			return "Q";
		else if(n == 11)
			return "J";
		else if(n == 1)
			return "A";
		else
			return n+"";
	}
	public Card getCard()
	{
		return c;
	}
	@Override
	public void paint(Graphics g)
	{
		g.drawImage(img,0,0,null);
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
		g.drawString(suit, 20, 40);
		g.drawString(number, 20, 30);
	}

}
