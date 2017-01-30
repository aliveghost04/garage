package gestion.garage.vehiculo;

public class Moto implements Vehiculo {

	private int id;
	private double kilometraje;
	private int cantidadRuedas;
	private String marca;
	private int cilindrada;
	
	public Moto(String marca, double kilometraje, int cilindrada, int cantidadRuedas) {
		this.marca = marca;
		this.cilindrada = cilindrada;
		this.kilometraje = kilometraje;
		this.cantidadRuedas = cantidadRuedas;
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

	public int getCilindrada() {
		return cilindrada;
	}
	
	public String toString() {
		return "Moto: ID registro # " + id + " " + marca + " tiene un kilometraje de " +
				kilometraje + " km, tiene una cilindrada de "+ cilindrada + " CC";
	}
	
}
