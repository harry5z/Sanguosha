package events;

import java.util.ArrayList;

import core.Card;
import core.Event;
import core.Framework;
import core.Operation;
import core.PlayerInfo;
import player.PlayerOriginal;
import player.PlayerOriginalClientComplete;
import player.PlayerOriginalClientSimple;
import basics.Attack;
import basics.Dodge;
import update.Damage;
import update.UseOfCards;

public class AttackEvent implements Operation, Event
{
	public static final int TARGET_SELECTION = 1;
	
	public static final int BEFORE_TARGET_LOCKED = 2;//target operation
	public static final int TARGET_LOCKED = 3;//source & target operation
	public static final int AFTER_TARGET_LOCKED_SKILLS = 4;//source operation
	public static final int AFTER_TARGET_LOCKED_WEAPONS = 5;//source operation
	public static final int DODGE_DECISION = 6;//target operation, for shield effects
	
	public static final int USING_DODGE = 7;//target operation, for target skills/shields
	public static final int AFTER_USING_DODGE = 8;//target operation, for target skills
	public static final int ATTACK_DODGED_SKILLS = 9;//source operation
	public static final int ATTACK_DODGED_WEAPONS = 10;//source operation
	
	public static final int ATTACK_NOT_DODGED_PREVENTION = 11;//only for skill: "fog"
	public static final int ATTACK_NOT_DODGED_ADDITION = 12;//only for skill: "gale"
	
	public static final int BEFORE_DAMAGE = 13;//source operation.last stage, for weapon effects
	public static final int DAMAGE = 14;//target operation, take damage
	public static final int END = 15;
	
	private PlayerInfo target;
	private PlayerInfo source;
	private int stage;
	private boolean dodgeable;
	private Attack cardUsedAsAttack;
	private Card dodge;
	private ArrayList<Card> cardsUsed;
	private Damage damage;
	private Event nextEvent;
	
//	public AttackEvent(PlayerOriginalClientComplete source, Attack attack)
//	{
//		this.source = source.getPlayerInfo();
//		cardUsedAsAttack = attack;
//		cardsUsed = new ArrayList<Card>();
//		cardsUsed.add(attack);
//		stage = TARGET_SELECTION;
//		dodgeable = true;
//		dodge = null;
//		enableTargets(source);
//	}
	public AttackEvent(PlayerOriginalClientComplete source, Attack attack, Event next)
	{
		this.source = source.getPlayerInfo();
		target = null;
		cardUsedAsAttack = attack;
		cardsUsed = new ArrayList<Card>();
		cardsUsed.add(attack);
		stage = TARGET_SELECTION;
		dodgeable = true;
		dodge = null;
		enableTargets(source);
		nextEvent = next;
	}

	private void endOfTargetOperation(PlayerOriginalClientComplete player)
	{
		player.setOperation(null);
		player.setCancelEnabled(false);
		if(dodge != null)
			player.setCardOnHandSelected(dodge, false);
		player.setAllCardsOnHandSelectable(false);
	}
	@Override
	public void frameworkOperation(Framework framework)
	{
		framework.sendToAllClients(this);
	}
	@Override
	public void playerOperation(PlayerOriginalClientComplete player) 
	{
		System.out.println("Attack stage "+stage);
		if(player.isEqualTo(target))//target operations
		{
			if(stage == BEFORE_TARGET_LOCKED)//target skills to change target
			{
				stage = TARGET_LOCKED;
				
				player.sendToMaster(this);//for now
			}
			else if(stage == DODGE_DECISION)//target skills/shields to cancel the attack
			{
				if(!player.isEquippedShield() || player.getShield().isRequiredToReact(cardUsedAsAttack))
					//no equipment or equipment cannot cancel the attack
				{
					stage = USING_DODGE;
					player.sendToMaster(this);
				}
				else // can cancel the attack. (in the future, take note of hero skills)
				{
					stage = END;
					player.sendToMaster(this);
				}
			}
			else if(stage == USING_DODGE)//target choose whether to dodge the attack
			{
				player.setOperation(this);//push it to target's stack
				player.setCardSelectableByName(Dodge.DODGE, true);//enable dodges
				player.setCancelEnabled(true);//enable cancel
			}
			else if(stage == AFTER_USING_DODGE)
			{
				stage++;
				player.sendToMaster(this);
			}
			else if(stage == ATTACK_NOT_DODGED_PREVENTION)
			{
				stage++;
				player.sendToMaster(this);
			}
			else if(stage == ATTACK_NOT_DODGED_ADDITION)
			{
				stage++;
				player.sendToMaster(this);
			}
			else if(stage == DAMAGE)
			{
				stage++;
				player.sendToMaster(damage);
			}
		}
		else if(player.isEqualTo(source))//source operation
		{
			if(stage == TARGET_LOCKED)
			{
				stage++;
				player.sendToMaster(this);
			}
			else if(stage == AFTER_TARGET_LOCKED_SKILLS)
			{
				stage++;
				player.sendToMaster(this);
			}
			else if(stage == AFTER_TARGET_LOCKED_WEAPONS)
			{
				stage++;
				player.sendToMaster(this);
			}
			else if(stage == ATTACK_DODGED_SKILLS)
			{
				stage++;
				player.sendToMaster(this);
			}
			else if(stage == ATTACK_DODGED_WEAPONS)
			{
				stage = END;
				player.sendToMaster(this);
			}
			else if(stage == BEFORE_DAMAGE)
			{
				stage++;
				player.sendToMaster(this);
			}
			else if(stage == END)
				player.sendToMaster(nextEvent);
		}
		else //bystanders...
		{
			System.out.println(player.getName()+ " is watching "+source.getName()+ " attacking "+target.getName()+" at stage "+stage);
		}
	}

	private void enableTargets(PlayerOriginalClientComplete source)
	{
		for(PlayerOriginalClientSimple p : source.getOtherPlayers())
			if(p.isAlive() && source.isPlayerInRange(p,source.getNumberOfPlayersAlive()))
				source.setTargetSelectable(p.getPlayerInfo(), true);//in the future, add skill decisions
	}
	@Override
	public void onPlayerSelected(PlayerOriginalClientComplete operator, PlayerOriginal player) 
	{
		if(stage == TARGET_SELECTION)//now selecting target
		{
			if(target == null)//set a target
			{
				target = player.getPlayerInfo();
				operator.selectTarget(target);
				operator.setConfirmEnabled(true);
				operator.setCancelEnabled(true);
			}
			else if(player.isEqualTo(target))//cancel target
			{
				operator.unselectTarget(target);
				target = null;
				operator.setConfirmEnabled(false);
			}
			else//change target
			{
				operator.unselectTarget(target);
				target = player.getPlayerInfo();
				operator.selectTarget(target);
			}
		}
		else
			System.err.println("AttackEvent: invalid player selection at stage "+stage);
	}
	@Override
	public void onConfirmedBy(PlayerOriginalClientComplete player) 
	{
		if(player.isEqualTo(source))
		{
			int amount = 1;//in the future, player.getDamageAmount (or similar)
			player.useAttack();
			if(player.isWineUsed())
			{
				amount++;
				player.useWine();
			}
			player.unselectTarget(target);
			player.setCardOnHandSelected(cardUsedAsAttack, false);
			damage = new Damage(amount,cardUsedAsAttack.getElement(),source,target,this);
			stage = BEFORE_TARGET_LOCKED;
			player.setOperation(null);
			player.sendToMaster(new UseOfCards(source,cardUsedAsAttack,this));
		}
		else if(player.isEqualTo(target))//target dodged
		{
			stage = AFTER_USING_DODGE;
			endOfTargetOperation(player);
			player.sendToMaster(new UseOfCards(target,dodge,this));
		}
		else
			System.err.println("AttackEvent: Invalid confirmation at stage "+stage);
	}
	@Override
	public void onCancelledBy(PlayerOriginalClientComplete player)
	{
		if(stage == TARGET_SELECTION)//not sent yet
		{
			cancelOperation(player,cardUsedAsAttack);
			player.setCancelEnabled(false);
		}
		else if(stage == USING_DODGE)//target operation
		{
			if(dodge != null)//unselect dodge
			{
				player.setCardOnHandSelected(dodge, false);
				player.setConfirmEnabled(false);
				dodge = null;
			}
			else // choose not to dodge
			{
				stage = ATTACK_NOT_DODGED_PREVENTION;
				endOfTargetOperation(player);
				player.sendToMaster(this);
			}
		}
		else
			System.err.println("AttackEvent: invalid cancellation at stage "+stage);
	}
	private void cancelOperation(PlayerOriginalClientComplete operator, Card card)
	{
		operator.setOperation(null);//no operation
		operator.setConfirmEnabled(false);//unable to confirm
		if(target != null)
			operator.unselectTarget(target);//target not selected
		for(PlayerOriginalClientSimple p : operator.getOtherPlayers())//no target selectable
			operator.setTargetSelectable(p.getPlayerInfo(), false);
	}
	@Override
	public void onCardSelected(PlayerOriginalClientComplete operator, Card card) 
	{
		if(operator.isEqualTo(target))//target select a card (must be dodge)
		{
			if(dodge != null)//unselect previous dodge
			{
				operator.setCardOnHandSelected(dodge, false);
				if(dodge.equals(card))//unselect dodge
				{
					dodge = null;
					operator.setConfirmEnabled(false);
				}
				else//change
				{
					dodge = card;
					operator.setCardOnHandSelected(card, true);
				}
			}
			else
			{
				dodge = card;
				operator.setCardOnHandSelected(card, true);
				operator.setConfirmEnabled(true);
			}
		}
		else
			System.err.println("AttackEvent: Bystander selecting cards");
	}
	@Override
	public void nextStep() 
	{
		// TODO Auto-generated method stub
		
	}
}
