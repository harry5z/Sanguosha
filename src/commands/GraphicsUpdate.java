package commands;

import core.server.Game;

/**
 * Client-side graphical update, which will not be
 * sent back to server
 * @author Harry
 *
 */
public abstract class GraphicsUpdate implements Command
{
	private static final long serialVersionUID = 8961410873999492290L;

	/*
	 * (non-Javadoc)
	 * There is no server-side operation for graphical update
	 * @see update.Update#ServerOperation(core.Game)
	 */
	@Override
	public void ServerOperation(Game framework) {}

}
