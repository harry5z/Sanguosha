package core.heroes.original;

import core.heroes.skills.YujinOriginalHeroSkill;

public class YuJin extends AbstractHero {
	
	private static final long serialVersionUID = 1L;

	public YuJin() {
		super(4, Faction.WEI, Gender.MALE, "YuJin", new YujinOriginalHeroSkill());
	}

}
