package core.heroes.original;

import core.heroes.skills.ZhugeliangFireAttackOriginalHeroSkill;
import core.heroes.skills.ZhugeliangSeeThroughHeroSkill;
import core.heroes.skills.ZhugeliangTaichiOriginalHeroSkill;

public class ZhugeliangRestingDragon extends AbstractHero {

	private static final long serialVersionUID = 1L;

	public ZhugeliangRestingDragon() {
		super(3, Faction.SHU, Gender.MALE, "Zhugeliang (Dragon)",
			new ZhugeliangTaichiOriginalHeroSkill(),
			new ZhugeliangSeeThroughHeroSkill(),
			new ZhugeliangFireAttackOriginalHeroSkill()
		);
	}

}
