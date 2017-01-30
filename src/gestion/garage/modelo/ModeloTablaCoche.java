package gestion.garage.modelo;

import gestion.garage.database.Consulta;
import gestion.garage.vehiculo.Coche;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

public class ModeloTablaCoche extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static ModeloTablaCoche instancia;
	private String[] columnasTabla = {"Marca", "Kilometraje", "Cantidad de puertas", "Cantidad de ruedas"};
	private ArrayList<Coche> coches;
	
	private ModeloTablaCoche() {
		coches = new ArrayList<Coche>();
		try {
			ResultSet rs = Consulta.getInstancia().hacerConsultaCoche();
			while (rs.next()) {
				Coche coche = new Coche(rs.getString("marca"), rs.getDouble("kilometraje"), 
						rs.getInt("cantidadRuedas"), rs.getInt("cantidadPuertas"));
				coche.setId(rs.getInt("id"));
				coches.add(coche);
			}
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | SQLException e) {
			JOptionPane.showMessageDialog(null, "Error con la base de datos, "
					+ "no se puede cargar la tabla: " + e.getMessage(), "Error", 
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static ModeloTablaCoche getInstancia() {
		return instancia == null ? instancia = new ModeloTablaCoche() : instancia;
	}
	
	public void retirarCoche(int pos) {
		coches.remove(pos);
		fireTableDataChanged();
	}
	
	public int getIdCoche(int pos) {
		return coches.get(pos).getId();
	}
	
	public void agregarCoche(Coche coche) {
		coches.add(coche);
		fireTableDataChanged();
	}
	
	@Override
	public int getColumnCount() {
		return columnasTabla.length;
	}

	@Override
	public int getRowCount() {
		return coches.size();
	}

	@Override
	public Object getValueAt(int x, int y) {
		
		String retorno = "";
		
		Coche coche = coches.get(x);
		
		switch(y) {
		case 0:
			retorno = coche.getMarca();
			break;
		case 1:
			retorno = String.valueOf(coche.getKilometraje());
			break;
		case 2:
			retorno = String.valueOf(coche.getCantidadPuerta());
			break;
		case 3:
			retorno = String.valueOf(coche.getCantidadRuedas());
			break;
		}
		
		return retorno;
	}
	
	public String getColumnName(int x) {
		return columnasTabla[x];
	}

}
