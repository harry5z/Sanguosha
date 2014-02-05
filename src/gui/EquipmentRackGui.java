package gui;

import java.awt.event.ActionListener;

import javax.swing.JPanel;

import cards.equipments.Equipment;
import listener.EquipmentListener;

public class EquipmentRackGui extends JPanel implements EquipmentListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8319619360735971266L;
	public static final int WIDTH = CardRackGui.HEIGHT;
	public static final int HEIGHT = WIDTH;
	private EquipmentGui weapon;
	private EquipmentGui shield;
	private EquipmentGui horsePlus;
	private EquipmentGui horseMinus;
	
	public EquipmentRackGui(ActionListener listener)
	{
		init();
		weapon.addActionListener(listener);
		shield.addActionListener(listener);
		horsePlus.addActionListener(listener);
		horseMinus.addActionListener(listener);
	}
	public EquipmentRackGui()
	{
		init();
	}
	private void init()
	{
		setSize(WIDTH,HEIGHT);
		setLayout(null);
		setLocation(0,PanelGui.HEIGHT-HEIGHT);
		weapon = new EquipmentGui(0);
		shield = new EquipmentGui(EquipmentGui.HEIGHT);
		horsePlus = new EquipmentGui(2*EquipmentGui.HEIGHT);
		horseMinus = new EquipmentGui(3*EquipmentGui.HEIGHT);
		add(weapon);
		add(shield);
		add(horsePlus);
		add(horseMinus);
	}
	@Override
	public void onEquipped(Equipment equipment)
	{
		switch(equipment.getEquipmentType())
		{
			case Equipment.WEAPON:
				weapon.setEquipment(equipment);
				break;
			case Equipment.SHIELD:
				shield.setEquipment(equipment);
				break;
			case Equipment.HORSEPLUS:
				horsePlus.setEquipment(equipment);
				break;
			case Equipment.HORSEMINUS:
				horseMinus.setEquipment(equipment);
				break;
			default:
				System.err.println("EquipmentRack: Unidentified Error");
		}
	}

	@Override
	public void onUnequipped(int type)
	{
		switch(type)
		{
		case Equipment.WEAPON:
			weapon.setEquipment(null);
			break;
		case Equipment.SHIELD:
			shield.setEquipment(null);
			break;
		case Equipment.HORSEPLUS:
			horsePlus.setEquipment(null);
			break;
		case Equipment.HORSEMINUS:
			horseMinus.setEquipment(null);
			break;
		default:
			System.err.println("EquipmentRack: Unidentified Error");
		}
	}
}
