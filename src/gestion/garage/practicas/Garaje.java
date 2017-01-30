package gestion.garage.practicas;

import gestion.garage.database.Consulta;
import gestion.garage.vehiculo.Coche;
import gestion.garage.vehiculo.Moto;
import gestion.garage.vehiculo.Vehiculo;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Garaje {
	
	private Vehiculo[] vehiculos;
	private int precioGomas;
	private int totalCambioGomas;
	private double totalKilometrajeMedio;
	private static Garaje instancia;
	
	public static void crearInstancia(int cantidadVehiculos, int precioGomas) {
		instancia = new Garaje(cantidadVehiculos, precioGomas);
	}
	
	public static Garaje getInstancia() {
		return instancia;
	}
	
	private Garaje(int cantidadVehiculos, int precioGomas) {
		vehiculos = new Vehiculo[cantidadVehiculos];
		this.precioGomas = precioGomas;
		cargarArray();
	}
	
	public boolean agregarVehiculo(Vehiculo tipoVehiculo) {
		for(int x = 0; x < vehiculos.length; x++) {
			if (vehiculos[x] == null) {
				vehiculos[x] = tipoVehiculo;
				return true;
			}
		}
		return false;
	}
	
	private void cargarArray() {
		int pos = 0;
		try {
			ResultSet rs = Consulta.getInstancia().hacerConsultaCoche();
			while(rs.next()) {
				Coche coche = new Coche(rs.getString("marca"), rs.getDouble("kilometraje"), 
						rs.getInt("cantidadRuedas"), rs.getInt("cantidadPuertas"));
				coche.setId(rs.getInt("id"));
				vehiculos[pos] = coche;
				pos++;
			}
			rs = Consulta.getInstancia().hacerConsultaMoto();
			while(rs.next()) {
				Moto moto = new Moto(rs.getString("marca"), rs.getDouble("kilometraje"), 
						rs.getInt("cilindrada"), rs.getInt("cantidadRuedas"));
				moto.setId(rs.getInt("id"));
				vehiculos[pos] = moto;
				pos++;
			}
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | SQLException e) {
			JOptionPane.showMessageDialog(null, "Error con la base de datos, "
					+ "no se pueden cargar los vehículos: " + e.getMessage(), "Error", 
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public boolean eliminarVehiculo(Vehiculo tipoVehiculo) {
		for(int x = 0; x < getCantidadVehiculos(); x++) {
			if (vehiculos[x] != null) {
				if (vehiculos[x].toString().equals(tipoVehiculo.toString())) {
					vehiculos[x] = null;
					return true;
				}
			}
		}
		return false;
	}
	
	public int getCantidadVehiculos() {
		int retorno = 0;
		
		for(int x = 0; x < vehiculos.length; x++) {
			if (vehiculos[x] != null) {
				retorno++;
			}
		}
		
		return retorno;
	}
	
	public double getKilometrajeMedio() {
		
		totalKilometrajeMedio = 0;
		int conteo = 0;
		for(int x = 0; x < vehiculos.length; x++) {
			if (vehiculos[x] != null) {
				conteo++;
				totalKilometrajeMedio += vehiculos[x].getKilometraje(); 
			}
		}
		if (totalKilometrajeMedio == 0) return 0;
		return (totalKilometrajeMedio / conteo);
	}
	
	public int getTotalCambioGomas() {
		
		totalCambioGomas = 0;
		
		for(int x = 0; x < vehiculos.length; x++) {
			if(vehiculos[x] != null) totalCambioGomas += vehiculos[x].getCantidadRuedas();
		}
		
		return (totalCambioGomas * precioGomas);
	}
}
