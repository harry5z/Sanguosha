package core;

import java.util.ArrayList;

import data.*;
import specials.*;

public class PlayerImpl 
{
	private ArrayList<Card> cardsOnHand;
	private ArrayList<Card> decisionArea;
	private boolean skipDetermine = false;
	private boolean skipDraw = false;
	private boolean skipDeal = false;
	private boolean skipDiscard = false;
	private Hero hero;
	private int role;
	
	public PlayerImpl()
	{
		cardsOnHand = new ArrayList<Card>();
		decisionArea = new ArrayList<Card>();
	}
	public int getCardsOnHandCount()
	{
		return cardsOnHand.size();
	}
	public void receiveCard(Card c)
	{
		cardsOnHand.add(c);
	}
	public void setIdentity(int role)
	{
		this.role = role;
	}
	public int getIdentity()
	{
		return role;
	}
	public void setHero(Hero h)
	{
		hero = h;
	}
	public Hero getHero()
	{
		return hero;
	}
	public void myTurn()
	{

		turnStart();
		turnDetermine();
		turnDraw();
		turnDeal();
		turnDiscard();
		turnEnd();
	}
	private void turnStart(){}
	private void turnDetermine()
	{

	}
	private void turnDraw()
	{

	}
	private void turnDeal()
	{
		if(skipDeal){skipDeal = false;return;}
		
	}
	private void turnDiscard()
	{

	}
	private void turnEnd(){}
	public void receive(Data d)
	{
		
	}
	
}
