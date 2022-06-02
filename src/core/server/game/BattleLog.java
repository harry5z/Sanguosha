package core.server.game;

import java.util.Collection;

import cards.Card;
import cards.Card.Color;
import cards.equipments.Equipment;
import core.heroes.skills.Skill;
import core.player.PlayerCompleteServer;
import core.server.game.Damage.Element;

public class BattleLog {
	
	private final String log;
	
	private BattleLog(String log) {
		this.log = log;
	}
	
	public String getLogMessage() {
		return log;
	}
	
	public BattleLog onPlayer(PlayerCompleteServer player) {
		return new BattleLog(String.format("%s on %s", log, formatPlayer(player)));
	}
	
	public BattleLog onPlayers(Collection<PlayerCompleteServer> players) {
		String msg = this.log;
		msg += " on";
		for (PlayerCompleteServer p : players) {
			msg += String.format(" %s", formatPlayer(p));
		}
		return new BattleLog(msg);
	}
	
	public BattleLog withCard(Card card) {
		return new BattleLog(String.format("%s with %s", log, formatCard(card)));
	}
	
	public BattleLog withCards(Collection<Card> cards) {
		String msg = this.log;
		msg += " with";
		for (Card c : cards) {
			msg += String.format(" %s", formatCard(c));
		}
		return new BattleLog(msg);
	}
	
	public BattleLog to(String action) {
		return new BattleLog(String.format("%s to %s", log, action));
	}
	
	public static BattleLog custom(String x) {
		return new BattleLog(x);
	}
	
	public static BattleLog playerADidX(PlayerCompleteServer a, String x) {
		return new BattleLog(String.format("%s %s", formatPlayer(a), x));
	}
	
	public static BattleLog playerAUsedSkill(PlayerCompleteServer a, Skill s) {
		return new BattleLog(String.format("%s used skill %s", formatPlayer(a), formatSkill(s)));
	}
	
	public static BattleLog playerASkillPassivelyTriggered(PlayerCompleteServer a, Skill s, String x) {
		return new BattleLog(String.format("%s's skill <b>%s</b> is triggered. %s", formatPlayer(a), formatSkill(s), x));
	}
	
	public static BattleLog playerAUsedEquipment(PlayerCompleteServer a, Equipment e) {
		return new BattleLog(String.format("%s activated equipment <b>%s</b>", formatPlayer(a), e.getName()));
	}
	
	public static BattleLog playerAEquipmentPassivelyTriggered(PlayerCompleteServer a, Equipment e, String x) {
		return new BattleLog(String.format("%s's equipment <b>%s</b> is triggered. %s", formatPlayer(a), e.getName(), x));
	}
	
	public static BattleLog playerADidXToCards(PlayerCompleteServer a, String x, Collection<Card> cards) {
		String msg = String.format("%s %s", formatPlayer(a), x);
		for (Card c : cards) {
			msg += String.format(" %s", formatCard(c));
		}
		return new BattleLog(msg);
	}
	
	public static BattleLog playerADamagedPlayerB(PlayerCompleteServer a, PlayerCompleteServer b, Damage damage) {
		String damageType = "";
		if (damage.getElement() == Element.FIRE) {
			damageType = "Fire ";
		} else if (damage.getElement() == Element.THUNDER) {
			damageType = "Thunder ";
		}
		if (a == null) { // damage without source
			return new BattleLog(String.format("%s took <b>%d</b> %sDamage", formatPlayer(b), damage.getAmount(), damageType));
		} else {
			return new BattleLog(String.format("%s dealt <b>%d</b> %sDamage to %s", formatPlayer(a), damage.getAmount(), damageType, formatPlayer(b)));
		}
	}
	
	public static String formatPlayer(PlayerCompleteServer p) {
		return String.format("<b>%s(%s)</b>", p.getHero().getName(), p.getName());
	}
	
	public static String formatSkill(Skill s) {
		return String.format("<b>%s</b>", s.getName());
	}
	
	public static String formatCard(Card c) {
		String suit = null;
		switch (c.getSuit()) {
			case DIAMOND:
				suit = "♦";
				break;
			case CLUB:
				suit = "♣";
				break;
			case HEART:
				suit = "♥";
				break;
			case SPADE:
				suit = "♠";
				break;
		}
		if (c.getColor() == Color.RED) {
			return String.format("<span style=\"color:red;\"><b>%s %s%d</b></span>", c.getName(), suit, c.getNumber());
		} else {
			return String.format("<span style=\"color:black;\"><b>%s %s%d</b></span>", c.getName(), suit, c.getNumber());

		}
	}
}
