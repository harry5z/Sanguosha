package core.heroes.original;

import core.heroes.skills.GanNingSuddenStrikeHeroSkill;

public class GanNing extends HeroOriginal {

	public GanNing() {
		super(4, Faction.WU, Gender.MALE, "Gan Ning", new GanNingSuddenStrikeHeroSkill());
	}

}
