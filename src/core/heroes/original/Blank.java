package core.heroes.original;

/**
 * A hero with no skills
 * For test use only
 * 
 * @author Harry
 *
 */
public class Blank extends AbstractHero {

	private static final long serialVersionUID = 1L;

	public Blank() {
		super(5, Faction.QUN, Gender.MALE, "Blank");
	}

}
