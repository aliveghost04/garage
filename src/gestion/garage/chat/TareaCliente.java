package gestion.garage.chat;

import gestion.garage.iu.Chat;

public class TareaCliente extends Thread {
	
	private Cliente cliente;
	
	public TareaCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public void run() {
		while (true) {
			Chat.getInstancia().visualizarMensaje((cliente.textoRecibido()));
		}
	}
}