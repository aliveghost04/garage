package gestion.garage.chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class TareaServidor extends Thread {
	
	private ArrayList<TareaServidor> clientes;
	private Scanner entrada;
	private PrintWriter salida;
	
	public TareaServidor(Socket cliente, ArrayList<TareaServidor> clientes) 
			throws IOException {
		
		this.clientes = clientes;
		
		entrada = new Scanner(cliente.getInputStream());
		salida = new PrintWriter(cliente.getOutputStream());
		enviar("Bienvenido al chat !!");
	}
	
	public void run() {
		
		while (true) {
			
			enviarCadaUsuario(textoRecibido());
		}
	}
	
	private void enviarCadaUsuario(String mensaje) {
		for(TareaServidor x: clientes) {
			if (x != this) x.enviar(mensaje);
		}
	}
	
	private void enviar(String mensaje) {
		
		salida.println(mensaje);
		salida.flush();
	}
	
	private String textoRecibido() {
		return entrada.nextLine();
	}
}