package core.server.game;

import java.util.Collection;

import cards.Card;
import cards.Card.Color;
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
	
	public static BattleLog custom(String x) {
		return new BattleLog(x);
	}
	
	public static BattleLog playerADidX(PlayerCompleteServer a, String x) {
		return new BattleLog(String.format("%s %s", formatPlayer(a), x));
	}
	
	public static BattleLog playerADidXToPlayerB(PlayerCompleteServer a, String x, PlayerCompleteServer b) {
		return new BattleLog(String.format("%s %s %s", formatPlayer(a), x, formatPlayer(b)));
	}
	
	public static BattleLog playerADidXToPlayers(PlayerCompleteServer a, String x, Collection<PlayerCompleteServer> players) {
		String msg = String.format("%s %s", formatPlayer(a), x);
		for (PlayerCompleteServer p : players) {
			msg += String.format(" %s", formatPlayer(p));
		}
		return new BattleLog(msg);
	}
	
	public static BattleLog playerADidXToPlayersWithCards(PlayerCompleteServer a, String x, Collection<PlayerCompleteServer> players, Collection<Card> cards) {
		String msg = String.format("%s %s", formatPlayer(a), x);
		for (PlayerCompleteServer p : players) {
			msg += String.format(" %s", formatPlayer(p));
		}
		msg += " with";
		for (Card c : cards) {
			msg += String.format(" %s", formatCard(c));
		}
		return new BattleLog(msg);
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
		return new BattleLog(String.format("%s dealt <b>%d</b> %sDamage to %s", formatPlayer(a), damage.getAmount(), damageType, formatPlayer(b)));
	}
	
	private static String formatPlayer(PlayerCompleteServer p) {
		return String.format("<b>%s(%s)</b>", p.getHero().getName(), p.getName());
	}
	
	private static String formatCard(Card c) {
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
