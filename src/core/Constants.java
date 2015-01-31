package core;

import java.awt.GraphicsEnvironment;

public class Constants {

	private Constants() {}
	
	public static final int BORDER_WIDTH = 8;
	public static final int SCREEN_WIDTH = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth();
	public static final int SCREEN_HEIGHT = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight();
}
