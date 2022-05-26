package core.heroes.original;

import core.heroes.skills.WeiYanRampageHeroSkill;

public class WeiYan extends AbstractHero {
	
	private static final long serialVersionUID = 1L;

	public WeiYan() {
		super(4, Faction.SHU, Gender.MALE, "Wei Yan", new WeiYanRampageHeroSkill());
	}

}
