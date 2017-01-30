package gestion.garage.database;

import gestion.garage.vehiculo.Coche;
import gestion.garage.vehiculo.Moto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Consulta {
	
	private Connection conexion;
	private Statement enunciado;
	private PreparedStatement ps;
	private static Consulta instancia;
	private ResultSet rs;
	
	public static Consulta getInstancia() throws InstantiationException, 
	IllegalAccessException, ClassNotFoundException, SQLException {
		return instancia == null ? instancia = new Consulta() : instancia;
	}
	
	private Consulta() throws InstantiationException, IllegalAccessException, 
	ClassNotFoundException, SQLException {
		conexion = Conexion.getInstancia().getConexion();
		enunciado = conexion.createStatement();
	}
	
	public ResultSet hacerConsultaCoche() throws SQLException {
		return enunciado.executeQuery("SELECT id, marca, kilometraje, "
				+ "cantidadRuedas, cantidadPuertas FROM coche");
	}
	
	public int obtenerCantidadVehiculos() throws SQLException {
		int retorno = 0;
		rs = enunciado.executeQuery("SELECT COUNT(*) FROM moto");
		if (rs.next()) retorno += rs.getInt(1);
		rs = enunciado.executeQuery("SELECT COUNT(*) FROM coche");
		if (rs.next()) retorno += rs.getInt(1);
		return retorno;
	}
	
	public ResultSet obtenerId() throws SQLException {
		return enunciado.executeQuery("SELECT last_insert_id()");
	}
	
	public ResultSet hacerConsultaMoto() throws SQLException {
		return enunciado.executeQuery("SELECT id, marca, kilometraje, "
				+ "cilindrada, cantidadRuedas FROM moto");
	}
	
	public void eliminarMoto(int id) throws SQLException {
		ps = conexion.prepareStatement("DELETE FROM moto WHERE id = ?");
		ps.setInt(1, id);
		ps.executeUpdate();
	}
	
	public void eliminarCoche(int id) throws SQLException {
		ps = conexion.prepareStatement("DELETE FROM coche WHERE id = ?");
		ps.setInt(1, id);
		ps.executeUpdate();
	}
	
	public Coche obtenerCoche(int id) throws SQLException {
		ps = conexion.prepareStatement("SELECT id, marca, kilometraje, cantidadRuedas, "
				+ "cantidadPuertas FROM coche WHERE id = ?");
		ps.setInt(1, id);
		rs = ps.executeQuery();
		rs.next();
		Coche coche = new Coche(rs.getString("marca"), rs.getDouble("kilometraje"), 
				rs.getInt("cantidadRuedas"), rs.getInt("cantidadPuertas"));
		coche.setId(rs.getInt("id"));
		return coche;
	}
	
	public Moto obtenerMoto(int id) throws SQLException {
		ps = conexion.prepareStatement("SELECT id, marca, kilometraje, cilindrada, "
				+ "cantidadRuedas FROM moto WHERE id = ?");
		ps.setInt(1, id);
		rs = ps.executeQuery();
		rs.next();
		Moto moto = new Moto(rs.getString("marca"), rs.getDouble("kilometraje"), 
				rs.getInt("cilindrada"), rs.getInt("cantidadRuedas"));
		moto.setId(rs.getInt("id"));
		return moto;
	}
	
	public void agregarCoche(Coche coche) throws SQLException {
		ps = conexion.prepareStatement("INSERT INTO coche VALUES(null, ?, ?, ?, ?)");
		ps.setString(1, coche.getMarca());
		ps.setDouble(2, coche.getKilometraje());
		ps.setInt(3, coche.getCantidadRuedas());
		ps.setInt(4, coche.getCantidadPuerta());
		ps.execute();
	}
	
	public void agregarMoto(Moto moto) throws SQLException {
		ps = conexion.prepareStatement("INSERT INTO moto VALUES(null, ?, ?, ?, ?)");
		ps.setString(1, moto.getMarca());
		ps.setDouble(2, moto.getKilometraje());
		ps.setInt(3, moto.getCilindrada());
		ps.setInt(4, moto.getCantidadRuedas());
		ps.execute();
	}
}
