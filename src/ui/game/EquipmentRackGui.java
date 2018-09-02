package ui.game;

import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.JPanel;

import cards.equipments.Equipment;
import cards.equipments.Equipment.EquipmentType;
import core.client.GamePanel;
import core.heroes.Hero;
import listeners.game.EquipmentListener;

public class EquipmentRackGui extends JPanel implements EquipmentListener {

	private static final long serialVersionUID = 8319619360735971266L;
	
	public static final int WIDTH = CardRackGui.HEIGHT;
	public static final int HEIGHT = WIDTH;
	
	private EquipmentGui weapon;
	private EquipmentGui shield;
	private EquipmentGui horsePlus;
	private EquipmentGui horseMinus;

	public EquipmentRackGui(GamePanel<? extends Hero> panel) {
		init();
		ActionListener listener = e -> panel.getCurrentOperation().onEquipmentClicked((EquipmentGui) e.getSource());
		weapon.addActionListener(listener);
		shield.addActionListener(listener);
		horsePlus.addActionListener(listener);
		horseMinus.addActionListener(listener);
	}

	public EquipmentRackGui() {
		init();
	}

	private void init() {
		setSize(WIDTH, HEIGHT);
		setLayout(null);
		setLocation(0, GamePanelGui.HEIGHT - HEIGHT);
		weapon = new EquipmentGui(0);
		shield = new EquipmentGui(EquipmentGui.HEIGHT);
		horsePlus = new EquipmentGui(2 * EquipmentGui.HEIGHT);
		horseMinus = new EquipmentGui(3 * EquipmentGui.HEIGHT);
		add(weapon);
		add(shield);
		add(horsePlus);
		add(horseMinus);
	}

	@Override
	public void onEquipped(Equipment equipment) {
		switch (equipment.getEquipmentType()) {
		case WEAPON:
			weapon.setEquipment(equipment);
			break;
		case SHIELD:
			shield.setEquipment(equipment);
			break;
		case HORSEPLUS:
			horsePlus.setEquipment(equipment);
			break;
		case HORSEMINUS:
			horseMinus.setEquipment(equipment);
			break;
		default:
			System.err.println("EquipmentRack: Unidentified Error");
		}
	}

	@Override
	public void onUnequipped(EquipmentType type) {
		switch (type) {
		case WEAPON:
			weapon.setEquipment(null);
			break;
		case SHIELD:
			shield.setEquipment(null);
			break;
		case HORSEPLUS:
			horsePlus.setEquipment(null);
			break;
		case HORSEMINUS:
			horseMinus.setEquipment(null);
			break;
		default:
			System.err.println("EquipmentRack: Unidentified Error");
		}
	}
	
	public void setActivatable(Collection<EquipmentType> types, boolean activatable) {
		if (types.contains(EquipmentType.WEAPON)) {
			this.weapon.setActivatable(activatable);
		}
		if (types.contains(EquipmentType.SHIELD)) {
			this.shield.setActivatable(activatable);
		}
		if (types.contains(EquipmentType.HORSEPLUS)) {
			this.horsePlus.setActivatable(activatable);
		}
		if (types.contains(EquipmentType.HORSEMINUS)) {
			this.horseMinus.setActivatable(activatable);
		}
	}
	
	public void setActivated(Collection<EquipmentType> types, boolean activated) {
		if (types.contains(EquipmentType.WEAPON)) {
			this.weapon.setActivated(activated);
		}
		if (types.contains(EquipmentType.SHIELD)) {
			this.shield.setActivated(activated);
		}
		if (types.contains(EquipmentType.HORSEPLUS)) {
			this.horsePlus.setActivated(activated);
		}
		if (types.contains(EquipmentType.HORSEMINUS)) {
			this.horseMinus.setActivated(activated);
		}
	}

}
