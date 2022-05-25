package core.heroes.original;

import core.heroes.skills.YujinOriginalHeroSkill;

public class YuJin extends HeroOriginal {
	
	public YuJin() {
		super(4, Faction.WEI, Gender.MALE, "YuJin", new YujinOriginalHeroSkill());
	}

}
