package core.heroes.original;

import core.heroes.skills.GanNingSuddenStrikeHeroSkill;

public class GanNing extends AbstractHero {

	private static final long serialVersionUID = 1L;

	public GanNing() {
		super(4, Faction.WU, Gender.MALE, "Gan Ning", new GanNingSuddenStrikeHeroSkill());
	}

}
