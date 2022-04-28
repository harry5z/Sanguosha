package core.heroes.original;

import core.heroes.skills.ZhugeliangFireAttackOriginalHeroSkill;
import core.heroes.skills.ZhugeliangTaichiOriginalHeroSkill;

public class ZhugeliangHiddenDragon extends HeroOriginal {

	public ZhugeliangHiddenDragon() {
		super(3, Force.SHU, Gender.MALE, "Zhugeliang (Dragon)",
			new ZhugeliangTaichiOriginalHeroSkill(),
			new ZhugeliangFireAttackOriginalHeroSkill()
		);
	}

}
