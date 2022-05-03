package ui.game;

import java.util.Collection;
import java.util.function.Consumer;

import javax.swing.JPanel;

import cards.equipments.Equipment;
import cards.equipments.Equipment.EquipmentType;
import core.client.GamePanel;
import listeners.game.EquipmentListener;
import ui.game.interfaces.EquipmentRackUI;
import ui.game.interfaces.EquipmentUI;

public class EquipmentRackGui extends JPanel implements EquipmentListener, EquipmentRackUI {

	private static final long serialVersionUID = 8319619360735971266L;
	
	public static final int WIDTH = CardRackGui.HEIGHT;
	public static final int HEIGHT = WIDTH;
	
	private final GamePanel panel;
	
	private final EquipmentGui weapon;
	private final EquipmentGui shield;
	private final EquipmentGui horsePlus;
	private final EquipmentGui horseMinus;
	
	private Consumer<EquipmentUI> weaponAction = null;
	private Consumer<EquipmentUI> shieldAction = null;
	private Consumer<EquipmentUI> horsePlusAction = null;
	private Consumer<EquipmentUI> horseMinusAction = null;

	public EquipmentRackGui(GamePanel panel) {
		this.panel = panel;
		setSize(WIDTH, HEIGHT);
		setLayout(null);
		setLocation(0, GamePanelGui.HEIGHT - HEIGHT);
		weapon = new EquipmentGui(0);
		weapon.addActionListener(e -> {
			this.weaponAction.accept(weapon);
		});
		shield = new EquipmentGui(EquipmentGui.HEIGHT);
		shield.addActionListener(e -> {
			this.shieldAction.accept(shield);
		});
		horsePlus = new EquipmentGui(2 * EquipmentGui.HEIGHT);
		horsePlus.addActionListener(e -> {
			this.horsePlusAction.accept(horsePlus);
		});
		horseMinus = new EquipmentGui(3 * EquipmentGui.HEIGHT);
		horseMinus.addActionListener(e -> {
			this.horseMinusAction.accept(horseMinus);
		});
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
				equipment.onEquipped(this.panel);
				break;
			case SHIELD:
				shield.setEquipment(equipment);
				equipment.onEquipped(this.panel);
				break;
			case HORSEPLUS:
				horsePlus.setEquipment(equipment);
				equipment.onEquipped(this.panel);
				break;
			case HORSEMINUS:
				horseMinus.setEquipment(equipment);
				equipment.onEquipped(this.panel);
				break;
			default:
				System.err.println("EquipmentRack: Unidentified Error");
		}
	}

	@Override
	public void onUnequipped(EquipmentType type) {
		Equipment e;
		switch (type) {
			case WEAPON:
				e = weapon.getEquipment();
				weapon.setEquipment(null);
				e.onUnequipped(this.panel);
				break;
			case SHIELD:
				e = shield.getEquipment();
				shield.setEquipment(null);
				e.onUnequipped(this.panel);
				break;
			case HORSEPLUS:
				e = horsePlus.getEquipment();
				horsePlus.setEquipment(null);
				e.onUnequipped(this.panel);
				break;
			case HORSEMINUS:
				e = horseMinus.getEquipment();
				horseMinus.setEquipment(null);
				e.onUnequipped(this.panel);
				break;
			default:
				System.err.println("EquipmentRack: Unidentified Error");
		}
	}
	
	@Override
	public void setActivatable(Collection<EquipmentType> types, Consumer<EquipmentUI> consumer) {
		this.setActivatableHelper(types, consumer, true);
	}
	
	@Override
	public void setUnactivatable(Collection<EquipmentType> types) {
		this.setActivatableHelper(types, null, false);
	}
	
	private void setActivatableHelper(Collection<EquipmentType> types, Consumer<EquipmentUI> action, boolean activatable) {
		if (types.contains(EquipmentType.WEAPON)) {
			this.weaponAction = action;
			this.weapon.setActivatable(activatable);
		}
		if (types.contains(EquipmentType.SHIELD)) {
			this.shieldAction = action;
			this.shield.setActivatable(activatable);
		}
		if (types.contains(EquipmentType.HORSEPLUS)) {
			this.horsePlusAction = action;
			this.horsePlus.setActivatable(activatable);
		}
		if (types.contains(EquipmentType.HORSEMINUS)) {
			this.horseMinusAction = action;
			this.horseMinus.setActivatable(activatable);
		}
	}
	
	@Override
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
