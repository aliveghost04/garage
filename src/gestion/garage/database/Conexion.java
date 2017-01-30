package gestion.garage.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import gestion.garage.property.Configuracion;

public class Conexion {
	
	private static Conexion instancia;
	private Connection conexion;
	
	public static Conexion getInstancia() throws InstantiationException, 
	IllegalAccessException, ClassNotFoundException, SQLException {
		
		return instancia == null ? instancia = new Conexion() : instancia;
	}
	
	private Conexion() throws InstantiationException, IllegalAccessException, 
	ClassNotFoundException, SQLException {
		
		Configuracion config = Configuracion.getInstancia();
		Class.forName(config.getDriver()).newInstance();
		conexion = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword());
	}
	
	public Connection getConexion() {
		return conexion;
	}
}
