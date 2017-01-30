package gestion.garage.property;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JOptionPane;

public class ManejadorProperties {
	
	private static ManejadorProperties instancia;
	private Properties proper = new Properties();
	private Configuracion config;
	
	public static ManejadorProperties getInstancia() {
		return instancia == null ? instancia = new ManejadorProperties() : instancia;
	}
	
	private ManejadorProperties() {
		
		File archivo = new File("config.cfg");
		if (archivo.exists()) {
			try {
				FileInputStream fis = new FileInputStream(archivo);
				cargarDatos(fis);
			} catch (FileNotFoundException e) {
				mensajeDeError("No se encontró el archivo de configuracion 'config.cfg'");
				System.exit(0);
			} catch (IOException e) {
				mensajeDeError("Error al cargar el archivo 'config.cfg'");
				System.exit(0);
			}
		} else {
			mensajeDeError("No se encontró el archivo de configuracion 'config.cfg'");
			System.exit(0);
		}
	}
	
	private void cargarDatos(FileInputStream fis) throws IOException {
		
		proper.load(fis);
		config = Configuracion.getInstancia();
		config.setDriver(proper.getProperty("driver"));
		config.setServidor(proper.getProperty("servidor"));
		config.setDatabase(proper.getProperty("database"));
		config.setPort(proper.getProperty("port"));
		config.setUser(proper.getProperty("user"));
		config.setPassword(proper.getProperty("password"));
	}
	
	private void mensajeDeError(String mensaje) {
		JOptionPane.showMessageDialog(null, mensaje,
				"Error", JOptionPane.ERROR_MESSAGE);
	}
}
