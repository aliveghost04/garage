package gestion.garage.modelo;

import gestion.garage.database.Consulta;
import gestion.garage.vehiculo.Moto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

public class ModeloTablaMotos extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static ModeloTablaMotos instancia;
	private String[] columnasTabla = {"Marca", "Kilometraje", "Cilindrada", "Cantidad de ruedas"};
	private ArrayList<Moto> motos;
	
	private ModeloTablaMotos() {
		motos = new ArrayList<Moto>();
		try {
			ResultSet rs = Consulta.getInstancia().hacerConsultaMoto();
			while(rs.next()) {
				Moto moto = new Moto(rs.getString("marca"), rs.getDouble("kilometraje"), 
						rs.getInt("cilindrada"), rs.getInt("cantidadRuedas"));
				moto.setId(rs.getInt("id"));
				motos.add(moto);
			}
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | SQLException e) {
			JOptionPane.showMessageDialog(null, "Error con la base de datos, "
					+ "no se puede cargar la tabla: " + e.getMessage(), "Error", 
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void retirarMoto(int pos) {
		motos.remove(pos);
		fireTableDataChanged();
	}
	
	public int getIdMoto(int pos) {
		return motos.get(pos).getId();
	}
	
	public void agregarMoto(Moto moto) {
		motos.add(moto);
		fireTableDataChanged();
	}
	
	public static ModeloTablaMotos getInstancia() {
		return instancia == null ? instancia = new ModeloTablaMotos() : instancia;
	}
	
	@Override
	public int getColumnCount() {
		return columnasTabla.length;
	}

	@Override
	public int getRowCount() {
		return motos.size();
	}

	@Override
	public Object getValueAt(int x, int y) {
		
		String retorno = "";
		
		Moto moto = motos.get(x);
		
		switch(y) {
		case 0:
			retorno = moto.getMarca();
			break;
		case 1:
			retorno = String.valueOf(moto.getKilometraje());
			break;
		case 2:
			retorno = String.valueOf(moto.getCilindrada());
			break;
		case 3:
			retorno = String.valueOf(moto.getCantidadRuedas());
			break;
		}
		
		return retorno;
	}
	
	public String getColumnName(int x) {
		return columnasTabla[x];
	}

}
