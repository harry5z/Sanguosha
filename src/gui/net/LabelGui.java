package gui.net;

import java.awt.Font;

import javax.swing.JLabel;

public class LabelGui extends JLabel {
	
	private static final long serialVersionUID = 3886055119865195519L;

	public LabelGui(String text) {
		super(text);
		setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
		setHorizontalAlignment(JLabel.CENTER);
	}
}
