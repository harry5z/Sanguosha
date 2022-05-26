package core.heroes.original;

import core.heroes.skills.SunQuanReconsiderationHeroSkill;

public class SunQuan extends AbstractHero {

	private static final long serialVersionUID = 1L;

	public SunQuan() {
		super(4, Faction.WU, Gender.MALE, "Sun Quan",
			new SunQuanReconsiderationHeroSkill()
		);
	}

}
