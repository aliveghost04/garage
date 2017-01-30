package gestion.garage.iu;

import gestion.garage.chat.Cliente;
import gestion.garage.chat.Servidor;
import gestion.garage.chat.TareaCliente;
import gestion.garage.chat.TareaServidor;

import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class InterfazPrincipalChat extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPanel;
	private JTextField txtNick;
	private TareaServidor tareaServidor;
	private ArrayList<TareaServidor> clientes;
	private Servidor servidor;
	private Cliente cliente;
	private TareaCliente tareaCliente;
	private static InterfazPrincipalChat instancia;
	private String nick;
	
	public static InterfazPrincipalChat getInstancia() {
		return instancia == null ? instancia = new InterfazPrincipalChat() : instancia;
	}
	
	private InterfazPrincipalChat() {
		
		Font fuente = new Font("Tahoma", Font.PLAIN, 16);
		
		setTitle("Iniciando Chat...");
		setResizable(false);
		setSize(280, 265);
		contentPanel = new JPanel();
		setContentPane(contentPanel);
		contentPanel.setLayout(null);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		JLabel lblIntroduzcaSuNick = new JLabel("Introduzca su nick:");
		lblIntroduzcaSuNick.setFont(fuente);
		lblIntroduzcaSuNick.setBounds(67, 22, 135, 21);
		contentPanel.add(lblIntroduzcaSuNick);
		
		txtNick = new JTextField();
		txtNick.setBounds(54, 55, 159, 20);
		contentPanel.add(txtNick);
		txtNick.setColumns(10);
		
		clientes = new ArrayList<TareaServidor>();
		
		JButton btnIniciarServidor = new JButton("Iniciar Servidor");
		btnIniciarServidor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (isNickValido()) {
					nick = txtNick.getText().trim();
					try {
						servidor = new Servidor();
						servidor.iniciarServidor();
					} catch (IOException e) {
						mensajeError("El puerto esta en uso: " + e.getMessage());
						return;
					}
					new Thread(new Runnable() {
						public void run () {
							try {
								while (true) {
									Socket socketServidor = servidor.aceptarCliente();
									tareaServidor = new TareaServidor(socketServidor, clientes);
									clientes.add(tareaServidor);
									tareaServidor.start();
								}
							} catch (UnknownHostException e) {
								mensajeError("Servidor desconocido");
							} catch (IOException e) {
								mensajeError(e.getMessage());
							} 
						}
					}).start();
					try {
						cliente = new Cliente(nick, "localhost");
						nuevoCliente(cliente);
					} catch (IOException e) {
						mensajeError(e.getMessage());
					}
				}
			}
		});
		btnIniciarServidor.setBounds(54, 99, 159, 40);
		contentPanel.add(btnIniciarServidor);
		
		JButton btnConectarseRemotamente = new JButton("Conectarse Remotamente");
		btnConectarseRemotamente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (isNickValido()) {
					nick = txtNick.getText().trim();
					try {
						String ip = JOptionPane.showInputDialog(InterfazPrincipalChat.this,
								"Introduzca una IP o Host",	"Conectandose remotamente", 
								JOptionPane.INFORMATION_MESSAGE);
						if (ip == null || ip.trim().length() == 0) {
							mensajeError("IP no válida");
							return;
						}
						cliente = new Cliente(nick, ip);
						nuevoCliente(cliente);
					} catch (UnknownHostException e1) {
						mensajeError("Servidor desconocido");
					} catch (IOException e1) {
						mensajeError(e1.getMessage() + "aqu");
					}
				}
			}
		});
		btnConectarseRemotamente.setBounds(54, 150, 159, 40);
		contentPanel.add(btnConectarseRemotamente);
	}
	
	private boolean isNickValido() {
		
		String campo = txtNick.getText().trim();
		if (campo.length() < 3 ) {
			mensajeError("Nick muy corto: Tamaño MIN 3 caracteres");
			txtNick.requestFocus();
			return false;
		} 
		if (campo.length() > 10) {
			mensajeError("Nick muy largo: Tamaño MAX 10 caracteres");
			txtNick.requestFocus();
			return false;
		}
		return true;
	}
	
	private void mensajeError(String mensaje) {
		JOptionPane.showMessageDialog(InterfazPrincipalChat.this, mensaje, 
				"Error", JOptionPane.ERROR_MESSAGE);
	}
	
	private void nuevoCliente(Cliente cliente) throws IOException {
		tareaCliente = new TareaCliente(cliente);
		tareaCliente.start();
		Chat.getInstancia().setNick(nick);
		Chat.getInstancia().setVisible(true);
		setVisible(false);
		setCliente(cliente);
	}
	
	private void setCliente(Cliente cliente) {
		Chat.getInstancia().setCliente(cliente);
	}
}
