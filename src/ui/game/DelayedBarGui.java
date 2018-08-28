package ui.game;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import cards.Card;
import listeners.game.DelayedListener;
import utils.DelayedStackItem;
import utils.DelayedType;

public class DelayedBarGui extends JPanel implements DelayedListener {

	private static final long serialVersionUID = 1L;
	
	private Stack<DelayedIcon> stack;
	
	public DelayedBarGui() {
		this.stack = new Stack<>();
		this.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
	}

	@Override
	public void onDelayedAdded(Card card, DelayedType type) {
		DelayedIcon icon = new DelayedIcon(new DelayedStackItem(card, type));
		this.stack.push(icon);
		this.add(icon, 0);
		this.validate();
		this.repaint();
	}

	@Override
	public void onDelayedRemove(DelayedType type) {
		DelayedIcon icon = null;
		for (DelayedIcon elem : this.stack) {
			if (elem.item.type == type) {
				icon = elem;
				break;
			}
		}
		this.stack.removeIf(elem -> elem.item.type == type);
		this.remove(icon);
		this.validate();
		this.repaint();
	}
	
	private static class DelayedIcon extends JPanel {
		
		private static final long serialVersionUID = 1L;
		
		Image icon;
		DelayedStackItem item;
		
		DelayedIcon(DelayedStackItem item) {
			this.setPreferredSize(new Dimension(25, 25));
			this.item = item;
			try {
				switch(item.type) {
					case LIGHTNING:
						this.icon = ImageIO.read(getClass().getResource("cards/Lightning_marker.png"));
						break;
					case OBLIVION:
						this.icon = ImageIO.read(getClass().getResource("cards/Oblivion_marker.png"));
						break;
					case STARVATION:
						this.icon = ImageIO.read(getClass().getResource("cards/Starvation_marker.png"));
						break;
				}
			}  catch (IOException e) {
				System.err.println("Image not found: " + item.type.name());
			}
		}
		
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			g.drawImage(this.icon, 0, 0, 25, 25, null);
		}
	}

}
