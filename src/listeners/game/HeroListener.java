package listeners.game;

import core.heroes.Hero;
import core.player.Role;

public interface HeroListener {
	
	public void onRoleAssigned(Role role);
	
	public void onRoleRevealed(Role role);
	
	public void onHeroRegistered(Hero hero);
	
	public void onWineEffective(boolean effective);
	
	public void onFlipped(boolean flipped);
	
	public void onChained(boolean chained);

}
