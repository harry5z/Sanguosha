package core.heroes.original;

import core.heroes.skills.YuanShaoArrowSalvoHeroSkill;

public class YuanShao extends AbstractHero {

	private static final long serialVersionUID = 1L;

	public YuanShao() {
		super(4, Faction.QUN, Gender.MALE, "Yuan Shao",
			new YuanShaoArrowSalvoHeroSkill()
		);
	}

}
