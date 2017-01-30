package gestion.garage.iu;

import gestion.garage.chat.Cliente;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class Chat extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea txtAreaDatos;
	private JTextField txtSalida;
	private JButton btnEnviar;
	private static Chat instancia;
	private Cliente cliente;
	boolean client;
	private String nick;
	
	public static Chat getInstancia() {
		return instancia == null ? instancia = new Chat() : instancia;
	}
	
	private Chat() {
		
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 500, 380);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtSalida = new JTextField();
		txtSalida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				enviar();
			}
		});
		txtSalida.setBounds(10, 280, 464, 20);
		contentPane.add(txtSalida);
		txtSalida.setColumns(10);
		
		txtAreaDatos = new JTextArea();
		txtAreaDatos.setLineWrap(true);
		txtAreaDatos.setWrapStyleWord(true);
		txtAreaDatos.setEditable(false);
	
		JScrollPane scrollPane = new JScrollPane(txtAreaDatos);
		scrollPane.setBounds(10, 11, 464, 258);
		contentPane.add(scrollPane);
		
		btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				enviar();
			}
		});
		btnEnviar.setBounds(385, 311, 89, 23);
		contentPane.add(btnEnviar);
	}
	
	public JTextArea getTextAreaDatos() {
		return txtAreaDatos;
	}
	
	public JTextField getTextFieldSalida() {
		return txtSalida;
	}
	
	public void visualizarMensaje(final String mensaje) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				txtAreaDatos.append(mensaje + "\n");
			}
		});
		
	}
	
	private void enviar() {
		if (txtSalida.getText().trim().length() != 0) {
			cliente.enviar(nick + ": >> " + txtSalida.getText().trim());
			txtSalida.setText("");
		}
	}
	
	public void setNick(String nick) {
		this.nick = nick;
		setTitle("Chat - " + nick);
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}
