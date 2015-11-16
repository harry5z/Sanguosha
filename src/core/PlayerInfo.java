package core;

import java.io.Serializable;

/**
 * A small but representative class of Player, since players are uniquely identified by
 * names and positions
 * @author Harry
 *
 */
public class PlayerInfo implements Serializable {
	private static final long serialVersionUID = -6855827509699547590L;
	private final String name;
	private final int position;
	public PlayerInfo(String name,int position) {
		this.name = name;
		this.position = position;
	}
	public String getName() {
		return name;
	}
	public int getPosition() {
		return position;
	}
	@Override
	public int hashCode() {
		return Integer.toString(position).hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		return obj instanceof PlayerInfo
			? position == ((PlayerInfo) obj).position
			: false;
	}
}
