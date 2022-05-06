package core.heroes.original;

import core.heroes.skills.SunQuanReconsiderationHeroSkill;

public class SunQuan extends HeroOriginal {

	public SunQuan() {
		super(4, Force.WU, Gender.MALE, "Sun Quan",
			new SunQuanReconsiderationHeroSkill()
		);
	}

}
