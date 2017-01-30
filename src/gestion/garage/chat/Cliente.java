package gestion.garage.chat;

import gestion.garage.iu.Chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {
	
	private String nick;
	private Socket cliente;
	private Scanner entrada;
	private PrintWriter salida;
	private final int puerto = 9998;
	
	public Cliente(String nick, String ip) throws UnknownHostException, IOException {
		cliente = new Socket(ip, puerto);
		this.nick = nick;
		flujos();
		Chat.getInstancia().setVisible(true);
	}
	
	private void flujos() throws IOException {
		salida = new PrintWriter(cliente.getOutputStream());
		entrada = new Scanner(cliente.getInputStream());
		enviar("Se ha conectado el usuario: >> " + nick);
	}
	
	public void enviar(String mensaje) {
		Chat.getInstancia().visualizarMensaje(mensaje);
		salida.println(mensaje);
		salida.flush();
	}
	
	public String textoRecibido() {
		return entrada.nextLine();
	}
}