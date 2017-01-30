package gestion.garage.chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
	
	private ServerSocket servidor;
	private final int puerto = 9998;
	
	public void iniciarServidor() throws IOException {
		servidor = new ServerSocket(puerto);
	}
	
	public Socket aceptarCliente() throws IOException {
		Socket socketCliente = servidor.accept();
		return socketCliente;
	}
}
