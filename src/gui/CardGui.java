package gui;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
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
	private Card card;
	private String number;
	private Color color;
	private boolean known;
	private BufferedImage suit;
	private BufferedImage img;
	private BufferedImage img_darker;
	private Timer timer;
	private float alpha = 1f;
	private int currY = 0;
	public CardGui(Card card)
	{
		this.card = card;	
		setSize(WIDTH,HEIGHT);
		number = numToString(card.getNumber());
		readSuit(card.getSuit());
		readImage(card.getName());
		known = true;
		timer = new Timer(10,this);
	}
	/**
	 * Only showing the back of a card
	 */
	public CardGui()
	{
		setSize(WIDTH,HEIGHT);
		try 
		{
			img = ImageIO.read(getClass().getResource("cards/card_back.png"));
		} 
		catch (IOException e)
		{
			System.err.println("File not found");
		}
		known = false;
	}
	
	public boolean isKnown()
	{
		return known;
	}
	private void readImage(String name)
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
	protected void fade(float offset)
	{
		alpha -= offset;
		repaint();
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
		return card;
	}
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		Graphics2D graphics = (Graphics2D)g;
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
		graphics.setComposite(ac);
		

		if(known)
		{
			if(isEnabled())
				graphics.drawImage(img, 0, 0, null);
			else
				graphics.drawImage(img_darker, 0, 0, null);
			graphics.setFont(new Font(Font.MONOSPACED, Font.BOLD, 18));
			graphics.setColor(color);
			graphics.drawImage(suit, 10, 28,null);
			if(number.length() == 1)
				graphics.drawString(number, 15, 25);
			else
				graphics.drawString(number, 10, 25);
		}
		else
		{
			graphics.drawImage(img, 0, 0, null);
		}
	}

}
