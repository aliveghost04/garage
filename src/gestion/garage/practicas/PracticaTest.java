package gestion.garage.practicas;

import gestion.garage.iu.InterfazPrincipal;
import gestion.garage.property.ManejadorProperties;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class PracticaTest {
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		ManejadorProperties.getInstancia();
		new InterfazPrincipal().setVisible(true);
	}
}
