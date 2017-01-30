package gestion.garage.vehiculo;

public class Coche implements Vehiculo {

	private int id;
	private double kilometraje;
	private int cantidadRuedas;
	private String marca;
	private int cantidadPuertas;
	
	public Coche(String marca, double kilometraje, int cantidadRuedas, int cantidadPuertas) {
		this.marca = marca;
		this.kilometraje = kilometraje;
		this.cantidadRuedas = cantidadRuedas;
		this.cantidadPuertas = cantidadPuertas;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	@Override
	public double getKilometraje() {
		return kilometraje;
	}

	@Override
	public int getCantidadRuedas() {
		return cantidadRuedas;
	}

	@Override
	public String getMarca() {
		return marca;
	}

	public int getCantidadPuerta() {
		return cantidadPuertas;
	}
	
	public String toString() {
		return "Coche: ID registro # " + id + " " + marca + " tiene un kilometraje de " +
				kilometraje + " km, tiene "+ cantidadPuertas + " puertas";
	}

}
