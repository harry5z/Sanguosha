package core.heroes.original;

import core.heroes.skills.GanNingSuddenStrikeHeroSkill;

public class GanNing extends HeroOriginal {

	public GanNing() {
		super(4, Force.WU, Gender.MALE, "Gan Ning", new GanNingSuddenStrikeHeroSkill());
	}

}
