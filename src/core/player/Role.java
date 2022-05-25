package core.player;

import java.io.Serializable;
import java.util.List;

public enum Role implements Serializable {
	EMPEROR,
	LOYALIST,
	REBEL,
	USURPER;
	
	public static final List<Role> ROLES_LIST = List.of(
		EMPEROR,
		REBEL,
		USURPER,
		REBEL,
		LOYALIST,
		REBEL,
		REBEL,
		LOYALIST
	);
}
