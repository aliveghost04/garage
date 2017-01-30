package gestion.garage.property;

public class Configuracion {
	
	private static Configuracion instancia;
	private String servidor;
	private String port;
	private String database;
	private String user;
	private String password;
	private String driver;
	
	public String getUrl() {
		return servidor + ":" + port + "/" + database;
	}

	public String getDatabaseName() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public static Configuracion getInstancia() {
		return instancia == null ? instancia = new Configuracion() : instancia;
	}
	
	public void setServidor(String servidor) {
		this.servidor = servidor;
	}

	public void setPort(String port) {
		this.port = port;
	}

	private Configuracion() {
		
	}
	
}
