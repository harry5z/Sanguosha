package gui;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.Timer;

import cards.Card;
import cards.Card.Suit;

/**
 * Gui class for cards (on hand and at diposal area)
 * @author Harry
 *
 */
public class CardGui extends JButton implements ActionListener
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8973362684095284243L;
	public static final int WIDTH = 142;
	public static final int HEIGHT = 200;
	private Card c;
	private String number;
	private Color color;
	private Image suit;
	private Image img;
	private Image img_darker;
	private Timer timer;
	private int currY = 0;
	public CardGui(Card card)
	{
		c = card;	
		setSize(WIDTH,HEIGHT);
		number = numToString(c.getNumber());
		readSuit(card.getSuit());
		readName(c.getName());
		timer = new Timer(10,this);
	}
	private void readName(String name)
	{
		try
		{
			img = ImageIO.read(getClass().getResource("cards/"+name+".png"));
			img_darker = ImageIO.read(getClass().getResource("cards/"+name+"_darker.png"));
		} 
		catch (IOException e) 
		{
			System.err.println("File not found");
		}
	}
	protected void moveVerticallyTo(int pixel)
	{
		timer.stop();
		currY = pixel;
		timer.start();
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(getY() < currY)
		{
			setLocation(getX(),getY()+1);
		}
		else if(getY() > currY)
		{
			setLocation(getX(),getY()-1);
		}
		else
			timer.stop();
	}
	private void readSuit(Suit n)
	{
		try
		{
			switch(n)
			{
			case SPADE:
				suit = ImageIO.read(getClass().getResource("cards/spade.png"));
				color = Color.BLACK;
				break;
			case HEART:
				suit = ImageIO.read(getClass().getResource("cards/heart.png"));
				color = Color.RED;
				break;
			case CLUB:
				suit = ImageIO.read(getClass().getResource("cards/club.png"));
				color = Color.BLACK;
				break;
			case DIAMOND:
				suit = ImageIO.read(getClass().getResource("cards/diamond.png"));
				color = Color.RED;
				break;
			}
		}
		catch(IOException e)
		{
			System.err.println("File not found");
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
		super.paint(g);
		if(isEnabled())
			g.drawImage(img,0,0,null);
		else
			g.drawImage(img_darker, 0, 0, null);
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 18));
		g.setColor(color);
		g.drawImage(suit, 10, 28,null);
		if(number.length() == 1)
			g.drawString(number, 15, 25);
		else
			g.drawString(number, 10, 25);
	}

}
