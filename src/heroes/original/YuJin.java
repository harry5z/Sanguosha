package heroes.original;

import java.awt.Image;

import skills.Skill;

public class YuJin extends HeroOriginal
{
	public class Sturdy implements Skill
	{
		
	}
	public YuJin() 
	{
		super(4,Force.WEI,Gender.MALE,"YuJin", new Sturdy());
	}

	@Override
	public Image getHeroImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Image getCardImage() {
		// TODO Auto-generated method stub
		return null;
	}

}
