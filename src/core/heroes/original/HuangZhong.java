package core.heroes.original;

import core.heroes.skills.HuangZhongSharpshooterHeroSkill;

public class HuangZhong extends AbstractHero {
	
	private static final long serialVersionUID = 1L;

	public HuangZhong() {
		super(4, Faction.SHU, Gender.MALE, "Huang Zhong", new HuangZhongSharpshooterHeroSkill());
	}

}
